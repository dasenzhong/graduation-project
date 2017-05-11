package bishe.dgut.edu.cn.reallygoodapp.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/11.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Job implements Serializable {

    CompanyUser companyUser;	//工作所属

    String jobName;		//工作名名
    String jobType;		//工作类型
    String number;		//招募人数
    String education;	//学历
    String money;		//薪酬
    String decribe;		//描述
    String employProvince;		//招募省份
    String employCity;			//招募城市
    String employTown;			//招募城镇
    String workProvince;		//工作省份
    String workCity;			//工作城市
    String workTown;			//工作城镇
    String workAddress;			//详细地址

    public CompanyUser getCompanyUser() {
        return companyUser;
    }

    public void setCompanyUser(CompanyUser companyUser) {
        this.companyUser = companyUser;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDecribe() {
        return decribe;
    }

    public void setDecribe(String decribe) {
        this.decribe = decribe;
    }

    public String getEmployProvince() {
        return employProvince;
    }

    public void setEmployProvince(String employProvince) {
        this.employProvince = employProvince;
    }

    public String getEmployCity() {
        return employCity;
    }

    public void setEmployCity(String employCity) {
        this.employCity = employCity;
    }

    public String getEmployTown() {
        return employTown;
    }

    public void setEmployTown(String employTown) {
        this.employTown = employTown;
    }

    public String getWorkProvince() {
        return workProvince;
    }

    public void setWorkProvince(String workProvince) {
        this.workProvince = workProvince;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getWorkTown() {
        return workTown;
    }

    public void setWorkTown(String workTown) {
        this.workTown = workTown;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }
}
