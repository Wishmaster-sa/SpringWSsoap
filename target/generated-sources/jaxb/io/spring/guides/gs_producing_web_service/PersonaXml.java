//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.07.17 at 03:21:22 PM CEST 
//


package io.spring.guides.gs_producing_web_service;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for personaXml complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="personaXml"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="patronymic" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="birthDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="rnokpp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="unzr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pasport" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isChecked" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="CheckedRequest" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personaXml", propOrder = {
    "id",
    "firstName",
    "patronymic",
    "lastName",
    "age",
    "birthDate",
    "rnokpp",
    "unzr",
    "pasport",
    "isChecked",
    "checkedRequest"
})
public class PersonaXml {

    protected long id;
    @XmlElement(required = true)
    protected String firstName;
    @XmlElement(required = true)
    protected String patronymic;
    @XmlElement(required = true)
    protected String lastName;
    protected int age;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar birthDate;
    @XmlElement(required = true)
    protected String rnokpp;
    @XmlElement(required = true)
    protected String unzr;
    @XmlElement(required = true)
    protected String pasport;
    protected boolean isChecked;
    @XmlElement(name = "CheckedRequest", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar checkedRequest;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the patronymic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Sets the value of the patronymic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatronymic(String value) {
        this.patronymic = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the age property.
     * 
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the value of the age property.
     * 
     */
    public void setAge(int value) {
        this.age = value;
    }

    /**
     * Gets the value of the birthDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthDate(XMLGregorianCalendar value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the rnokpp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRnokpp() {
        return rnokpp;
    }

    /**
     * Sets the value of the rnokpp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRnokpp(String value) {
        this.rnokpp = value;
    }

    /**
     * Gets the value of the unzr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnzr() {
        return unzr;
    }

    /**
     * Sets the value of the unzr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnzr(String value) {
        this.unzr = value;
    }

    /**
     * Gets the value of the pasport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPasport() {
        return pasport;
    }

    /**
     * Sets the value of the pasport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPasport(String value) {
        this.pasport = value;
    }

    /**
     * Gets the value of the isChecked property.
     * 
     */
    public boolean isIsChecked() {
        return isChecked;
    }

    /**
     * Sets the value of the isChecked property.
     * 
     */
    public void setIsChecked(boolean value) {
        this.isChecked = value;
    }

    /**
     * Gets the value of the checkedRequest property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCheckedRequest() {
        return checkedRequest;
    }

    /**
     * Sets the value of the checkedRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCheckedRequest(XMLGregorianCalendar value) {
        this.checkedRequest = value;
    }

}
