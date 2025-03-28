/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ega.springwsoap.services;

import com.ega.springwsoap.HttpRequestUtils;
import com.ega.springwsoap.interfaces.PersonaInterface;
import com.ega.springwsoap.models.Answer;
import com.ega.springwsoap.models.LogRecord;
import com.ega.springwsoap.models.Persona;
import com.ega.springwsoap.repository.PersonaRepository;

import io.spring.guides.gs_producing_web_service.AddPersonaRequest;
import io.spring.guides.gs_producing_web_service.AddPersonaResponse;
import io.spring.guides.gs_producing_web_service.AnswerXml;
import io.spring.guides.gs_producing_web_service.DeletePersonaResponse;
import io.spring.guides.gs_producing_web_service.GetPersonaListResponse;
import io.spring.guides.gs_producing_web_service.PersonaXml;
import io.spring.guides.gs_producing_web_service.UpdatePersonaRequest;
import io.spring.guides.gs_producing_web_service.UpdatePersonaResponse;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.json.JSONArray;
import org.springframework.beans.BeanUtils;



/**
 * Це реалізація сервіса. 
 * 
 */
 //Ця анотація вказує SPRING що данний клас використовується як сервіс
@Service
//Ця анотація відноситься до компоненту Lombok. Вона допомогає створити усі конструктори класів та перемених яки відносятся до данного класу.
//Тут він потрібен для того, щоб ініціалізувати PersonaRepository repository і таким чином включити його в область видимості фреймворка SPRING
//(дивись в документаціі к фреймворку "впровадження залежностей через конструктор")
@AllArgsConstructor
//@Primary
public class PersonaServiceImpl implements PersonaInterface{

    //задаемо константу яка належить до інтерфейсу репозіторія та дозволяє працювати з ним.
    private final PersonaRepository repository;
    private final LogRecordService logService;
    //Ця анотація означає, що нам треба перевизначити цю процедуру/функцію
    //Спочатку дана функція задається в інтерфейсі класу, тут ми її перевизначаємо та реалізуємо.
    //Докладніше https://www.baeldung.com/java-override
    @Override
    //Функція яка повертає усіх людей з бази даних.
    public GetPersonaListResponse showAllPersons() {
        // спочатку создаємо клас Відповідь - який буде містити в себе результати запиту.
          Answer ans;
          //це конструктор класу за допомогою бібліотекі Lombok
          ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
          GetPersonaListResponse response = new GetPersonaListResponse();
          
          //реалізацію функції обгортаємо в спробу/виключення
          try{
            var result = repository.findAll();      //визиваемо SELECT з бази даних
            List <Persona> persons;

            persons = repository.findAll();
            persons.forEach(persona -> response.getPersonaXml().add((PersonaXml) persona.toXML()));
            
            //якщо визов функції не перервався помилкою, то вважаємо його успішним, та записуемо в Статус відповіді
            ans.setStatus(Boolean.TRUE);            
            ans.setDescr("Successfully request");   //В описі відповіді вказуемо що запит успішний.
            JSONArray arr = new JSONArray();
            result.forEach(persona -> arr.put(persona.toJSON()));
            ans.setResult(arr.toString());       //Тут результат відповіді.

          }catch (Exception ex){                    //якщо помилка
              ans.setDescr(ex.getMessage());        //надаємо опис помилки
          }

          //записуємо лог
          writeLog(ans);
          return response;           //повертаємо результат до контролера.
    }

    //Ця анотація означає, що нам треба перевизначити цю процедуру/функцію
    //Спочатку дана функція задається в інтерфейсі класу, тут ми її перевизначаємо та реалізуємо.
    //Докладніше https://www.baeldung.com/java-override
    @Override
    //Функція додає нову персону до бази данних
    public AddPersonaResponse addPersona(AddPersonaRequest request) {
            Answer ans;
            ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
            AddPersonaResponse response = new AddPersonaResponse();
            PersonaXml personaXml = new PersonaXml();

            BeanUtils.copyProperties(request, personaXml);
            Persona persona = new Persona(personaXml);
            try {
                repository.save(persona);
                ans.setStatus(Boolean.TRUE);
                ans.setDescr("Persona added successfully");
                ans.setResult(persona.toJSON().toString());       //Тут результат відповіді.
            }catch(Exception ex){
                ans.setDescr("Error: "+ex.getMessage());
            }
            response.setAnswerXml(ans.toXml());

            //записуємо лог
            writeLog(ans);
  
            return response;           //повертаємо результат до контролера.
    }

    //Ця анотація означає, що нам треба перевизначити цю процедуру/функцію
    //Спочатку дана функція задається в інтерфейсі класу, тут ми її перевизначаємо та реалізуємо.
    //Докладніше https://www.baeldung.com/java-override
    @Override
    //Функція знаходить та повертає персону по його rnokpp
    public GetPersonaListResponse find(String rnokpp) {
        Answer ans;
        ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
        GetPersonaListResponse response = new GetPersonaListResponse();
        Persona persona;
          
        try{
            var result = repository.findByRnokpp(rnokpp);
            //якщо визов функції не перервався помилкою, то вважаємо його успішним, та записуемо в Статус відповіді
            ans.setStatus(Boolean.TRUE);
            if(result==null){
                ans.setDescr("Person with RNOKPP: "+rnokpp+" not found in database");   //В описі відповіді вказуемо що запит успішний.
            }else{
                System.out.println(result.toXML().toString());
		response.getPersonaXml().add(result.toXML());
                ans.setDescr("Successfully request");   //В описі відповіді вказуемо що запит успішний.
                ans.setResult(result.toJSON().toString());       //Тут результат відповіді.
            }
        }catch (Exception ex){                    //якщо помилка
            ans.setStatus(Boolean.FALSE);            
            ans.setDescr(ex.getMessage());        //надаємо опис помилки
        }
          
           //записуємо лог
        writeLog(ans);
        return response;           //повертаємо результат до контролера.
    }

    //Ця анотація означає, що нам треба перевизначити цю процедуру/функцію
    //Спочатку дана функція задається в інтерфейсі класу, тут ми її перевизначаємо та реалізуємо.
    //Докладніше https://www.baeldung.com/java-override
    @Override
    //@Transactional
    //Функція оновлює дані персони
    public UpdatePersonaResponse updatePersona(UpdatePersonaRequest request) {
            Answer ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
            UpdatePersonaResponse response = new UpdatePersonaResponse();
            Persona persona;
            
            try {
                persona = repository.findByRnokpp(request.getRnokpp());
                BeanUtils.copyProperties(request, persona);
                repository.save(persona);
                ans.setStatus(Boolean.TRUE);
                ans.setDescr("Persona with RNOKPP "+request.getRnokpp()+" updated successfully");
            }catch(Exception ex){
                ans.setDescr("Error: "+ex.getMessage());
            }
            response.setAnswerXml(ans.toXml());

         
            //записуємо лог
            writeLog(ans);
            return response;           //повертаємо результат до контролера.
    }

    //Ця анотація означає, що нам треба перевизначити цю процедуру/функцію
    //Спочатку дана функція задається в інтерфейсі класу, тут ми її перевизначаємо та реалізуємо.
    //Докладніше https://www.baeldung.com/java-override
    @Override
    //Видалення запису з БД повинно бути транзакційним. Ця анотація робить це.
    @Transactional
    //Видаляє персону по РНОКПП
    public DeletePersonaResponse deletePersona(String rnokpp) {
            Answer ans;
            ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
            DeletePersonaResponse response = new DeletePersonaResponse();


            try{
              repository.deleteByRnokpp(rnokpp);
              //якщо визов функції не перервався помилкою, то вважаємо його успішним, та записуемо в Статус відповіді
              ans.setStatus(Boolean.TRUE);
              ans.setDescr("Successfully request");   //В описі відповіді вказуемо що запит успішний.
              ans.setResult("Persona with RNOKPP: "+rnokpp+" was deleted!");  //Тут результат відповіді.
            }catch (Exception ex){                    //якщо помилка
                ans.setDescr(ex.getMessage());        //надаємо опис помилки
            }
          response.setAnswerXml((AnswerXml) ans.toXml());
          
           //записуємо лог
           writeLog(ans);
          return response;           //повертаємо результат до контролера.
    }
    
    @Override
    //Функція проставляє признак isCheked для персони
    public Answer checkup(String rnokpp) {
          Answer ans;
          ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
          try{
            Persona persona = repository.findByRnokpp(rnokpp);
            ans.setStatus(Boolean.TRUE);            
            ans.setDescr("Successfully request");   //В описі відповіді вказуемо що запит успішний.
            if(persona == null){
                ans.setResult("Persona with rnokpp "+rnokpp+" not found in database!");       //Тут результат відповіді.                
            }else{
                persona.setIsChecked(Boolean.TRUE);
                repository.save(persona);
                //якщо визов функції не перервався помилкою, то вважаємо його успішним, та записуемо в Статус відповіді
                ans.setResult("Persona is checked!");       //Тут результат відповіді.
                
            }
          }catch (Exception ex){                    //якщо помилка
              ans.setDescr(ex.getMessage());        //надаємо опис помилки
          }
          
           //записуємо лог
           writeLog(ans);
          return ans;           //повертаємо результат до контролера.
    }

    @Override
    //Функція перевіряє поточний статус персони
    public Answer checkPersona(String rnokpp) {
          Answer ans;
          ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
          try{
            Persona persona = repository.findByRnokpp(rnokpp);
            ans.setStatus(Boolean.TRUE);            
            ans.setDescr("Successfully request");   //В описі відповіді вказуемо що запит успішний.
            if(persona == null){
                ans.setResult("Persona with rnokpp "+rnokpp+" not found in database!");       //Тут результат відповіді.                
            }else if(persona.getIsChecked()==true){
                //якщо визов функції не перервався помилкою, то вважаємо його успішним, та записуемо в Статус відповіді
                ans.setResult("Persona is checked!");       //Тут результат відповіді.
                
            }else {
                LocalDateTime dt = LocalDateTime.now();
                LocalDateTime dr = persona.getCheckedRequest();
                if((dr==null)||(dr.getYear()==1)){
                    persona.setCheckedRequest(dt);
                    repository.save(persona);
                    ans.setResult("Request for checking Persona in progress!");       //Тут результат відповіді.
                }else{
                    dr = dr.plusMinutes(5);
                    if (dt.isAfter(dr)){                    
                        persona.setIsChecked(Boolean.TRUE);
                        repository.save(persona);
                        ans.setResult("Persona is checked!");       //Тут результат відповіді.
                    }else{
                        ans.setResult("Checking Persona in progress!");       //Тут результат відповіді.
                    }
                    
                }
            }
          }catch (Exception ex){                    //якщо помилка
              ans.setDescr(ex.getMessage());        //надаємо опис помилки
          }
          
          //запис лога
          writeLog(ans);
          return ans;           //повертаємо результат до контролера.
    }

    //запис лога
    private void writeLog(Answer ans){
    
        LogRecord log = new LogRecord();
        
        log.setIp(HttpRequestUtils.getClientIpAddress());
        log.setHttpMethod(HttpRequestUtils.getHttpMethod());
        log.setError(!ans.getStatus());
        log.setHeaders(HttpRequestUtils.getHeaders());
        log.setResource(HttpRequestUtils.getPath());
        log.setResult(ans);
        log.setDescr(ans.getDescr());
        log.setBody("");
        
        
        logService.addRecord(log);
        
    }

    @Override
    //Пошук всіх персон по їх імені
    public GetPersonaListResponse findByFirstName(String firstName) {
          GetPersonaListResponse response = new GetPersonaListResponse();
          Answer ans;
          ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
          try{
            List<Persona> result = repository.findAllByFirstName(firstName);
            JSONArray arr = new JSONArray();
 
            //якщо визов функції не перервався помилкою, то вважаємо його успішним, та записуемо в Статус відповіді
            ans.setStatus(Boolean.TRUE);
            if(result.isEmpty()){
                ans.setDescr("Person with firstName: "+firstName+" not found in database");   //В описі відповіді вказуемо що запит успішний.
            }else{
                result.forEach(persona -> arr.put(persona.toJSON()));
                result.forEach(persona -> response.getPersonaXml().add((PersonaXml) persona.toXML()));
                ans.setDescr("Successfully request");   //В описі відповіді вказуемо що запит успішний.
                ans.setResult(Persona.listToJSON(result).toString());       //Тут результат відповіді.
            }
          }catch (Exception ex){                    //якщо помилка
            ans.setStatus(Boolean.FALSE);            
            ans.setDescr(ex.getMessage());        //надаємо опис помилки
          }
          
          //записуем лог
          writeLog(ans);
          return response;           //повертаємо результат до контролера.
          //return ans;           //повертаємо результат до контролера.
    }
    
    @Override
    //пошук всіх персон по початку їх призвища
    public GetPersonaListResponse findByLastName(String lastName) {
          GetPersonaListResponse response = new GetPersonaListResponse();
          Answer ans;
          ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
          try{
            List<Persona> result = repository.findAllByLastName(lastName);
            result.forEach(persona -> response.getPersonaXml().add((PersonaXml) persona.toXML()));
            //якщо визов функції не перервався помилкою, то вважаємо його успішним, та записуемо в Статус відповіді
            ans.setStatus(Boolean.TRUE);
            if(result.isEmpty()){
                System.out.println("Person with lastName: "+lastName+" not found in database");
                ans.setDescr("Person with lastName: "+lastName+" not found in database");   //В описі відповіді вказуемо що запит успішний.
            }else{
                ans.setDescr("Successfully request");   //В описі відповіді вказуемо що запит успішний.
                ans.setResult(Persona.listToJSON(result).toString());       //Тут результат відповіді.
            }
          }catch (Exception ex){                    //якщо помилка
            ans.setStatus(Boolean.FALSE);            
            ans.setDescr(ex.getMessage());        //надаємо опис помилки
          }
          
          //записуем лог
          writeLog(ans);
          return response;           //повертаємо результат до контролера.
    }


    @Override
    public GetPersonaListResponse findByBirthDate(String birthDate) {
          GetPersonaListResponse response = new GetPersonaListResponse();
          Answer ans;
          LocalDate ld;
          ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
          try{
              ld = LocalDate.parse(birthDate);
              
            List<Persona> result = repository.findByBirthDateBetween(ld, ld);
            result.forEach(persona -> response.getPersonaXml().add((PersonaXml) persona.toXML()));
            //якщо визов функції не перервався помилкою, то вважаємо його успішним, та записуемо в Статус відповіді
            ans.setStatus(Boolean.TRUE);
            if(result.isEmpty()){
                ans.setDescr("Person with Birth Date: "+birthDate+" not found in database");   //В описі відповіді вказуемо що запит успішний.
            }else{
                ans.setDescr("Successfully request");   //В описі відповіді вказуемо що запит успішний.
                ans.setResult(Persona.listToJSON(result).toString());       //Тут результат відповіді.
            }
          }catch (Exception ex){                    //якщо помилка
            ans.setStatus(Boolean.FALSE);            
            ans.setDescr(ex.getMessage());        //надаємо опис помилки
          }
          
          //записуем лог
          writeLog(ans);
          return response;           //повертаємо результат до контролера.
    }

    @Override
    public GetPersonaListResponse findByPasport(String pasport) {
          GetPersonaListResponse response = new GetPersonaListResponse();
          Answer ans;
          ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
          try{
            List<Persona> result = repository.findAllByPasport(pasport);
            result.forEach(persona -> response.getPersonaXml().add((PersonaXml) persona.toXML()));
            //якщо визов функції не перервався помилкою, то вважаємо його успішним, та записуемо в Статус відповіді
            ans.setStatus(Boolean.TRUE);
            if(result.isEmpty()){
                ans.setDescr("Person with pasport: "+pasport+" not found in database");   //В описі відповіді вказуемо що запит успішний.
            }else{
                ans.setDescr("Successfully request");   //В описі відповіді вказуемо що запит успішний.
                ans.setResult(Persona.listToJSON(result).toString());       //Тут результат відповіді.
            }
          }catch (Exception ex){                    //якщо помилка
            ans.setStatus(Boolean.FALSE);            
            ans.setDescr(ex.getMessage());        //надаємо опис помилки
          }
          
          //записуем лог
          writeLog(ans);
          return response;           //повертаємо результат до контролера.
    }

    @Override
    public GetPersonaListResponse findByUnzr(String unzr) {
          GetPersonaListResponse response = new GetPersonaListResponse();
          Answer ans;
          ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
          try{
            List<Persona> result = repository.findAllByUnzr(unzr);
            result.forEach(persona -> response.getPersonaXml().add((PersonaXml) persona.toXML()));
            //якщо визов функції не перервався помилкою, то вважаємо його успішним, та записуемо в Статус відповіді
            ans.setStatus(Boolean.TRUE);
            if(result.isEmpty()){
                ans.setDescr("Person with unzr: "+unzr+" not found in database");   //В описі відповіді вказуемо що запит успішний.
            }else{
                ans.setDescr("Successfully request");   //В описі відповіді вказуемо що запит успішний.
                ans.setResult(Persona.listToJSON(result).toString());       //Тут результат відповіді.
            }
          }catch (Exception ex){                    //якщо помилка
            ans.setStatus(Boolean.FALSE);            
            ans.setDescr(ex.getMessage());        //надаємо опис помилки
          }
          
          //записуем лог
          writeLog(ans);
          return response;           //повертаємо результат до контролера.
    }


}
