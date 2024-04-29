package com.classroom.rbac.demo;

import lombok.Data;
import java.util.Date;

@Data
public class TodoItem {
    private Integer ItemId;
    private Integer userId;
    private String name;
    private Date creationTime;
    private boolean completionStatus;
    private Date completionTime;
}
