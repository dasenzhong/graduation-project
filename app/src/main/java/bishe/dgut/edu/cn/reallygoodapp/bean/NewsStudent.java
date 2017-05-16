package bishe.dgut.edu.cn.reallygoodapp.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsStudent implements Serializable {

    Integer id;

    Date createDate;
    Date editDate;

    StudentUser studentUser;

    String newsText;

    Job job;

    boolean isRead;
    boolean isAgent;
    boolean isJob;
}
