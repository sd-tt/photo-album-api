package com.sdtt.photoalbumapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "images")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    private String name;

    private String extension;

    @JsonIgnore
    private String url;

    @JsonInclude
    @Transient
    private String base64Data;

    @JsonInclude
    @Transient
    private Integer length;

    @ManyToOne
    private User user;

    public Image() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @JsonIgnore
    public Boolean isEmpty() {
        return id == null && fullName == null && name == null && extension == null && url == null && base64Data == null;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", name='" + name + '\'' +
                ", extension='" + extension + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
