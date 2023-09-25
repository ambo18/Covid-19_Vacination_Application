package com.example.covid_19vaccination;

public class ModelRecord {

    String id, name, image, phone, address, dob, category, vaccineManufacturer, vaccineManufacturer1, vaccineShot, vaccineShot1, vaccineDate,
            vaccineDate1, age,
            guardian, addedTime, updatedTime;

    public ModelRecord(String id, String name, String image, String phone, String address,
                       String dob, String age, String guardian, String category, String vaccineManufacturer,
                       String vaccineManufacturer1, String vaccineShot, String vaccineShot1, String vaccineDate,
                       String vaccineDate1, String addedTime, String updatedTime) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.age = age;
        this.guardian = guardian;
        this.category = category;
        this.vaccineManufacturer = vaccineManufacturer;
        this.vaccineManufacturer1 = vaccineManufacturer1;
        this.vaccineShot = vaccineShot;
        this.vaccineShot1 = vaccineShot1;
        this.vaccineDate = vaccineDate;
        this.vaccineDate1 = vaccineDate1;
        this.addedTime = addedTime;
        this.updatedTime = updatedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVaccineManufacturer() {
        return vaccineManufacturer;
    }

    public String getVaccineManufacturer1() {
        return vaccineManufacturer1;
    }

    public String getVaccineShot() {
        return vaccineShot;
    }

    public String getVaccineShot1() {
        return vaccineShot1;
    }

    public String getVaccineDate() {
        return vaccineDate;
    }

    public String getVaccineDate1() {
        return vaccineDate1;
    }

    public void setVaccineManufacturer(String vaccineManufacturer) {
        this.vaccineManufacturer = vaccineManufacturer;
    }

    public void setVaccineManufacturer1(String vaccineManufacturer1) {
        this.vaccineManufacturer1 = vaccineManufacturer1;
    }

    public void setVaccineShot(String vaccineShot) {
        this.vaccineShot = vaccineShot;
    }

    public void setVaccineShot1(String vaccineShot1) {
        this.vaccineShot1 = vaccineShot1;
    }

    public void setVaccineDate(String vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

    public void setVaccineDate1(String vaccineDate1) {
        this.vaccineDate1 = vaccineDate1;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}
