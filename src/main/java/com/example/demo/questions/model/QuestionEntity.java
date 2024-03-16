package com.example.demo.questions.model;

import com.example.demo.core.model.BaseEntity;
import com.example.demo.tests.model.TestEntity;

public class QuestionEntity  extends BaseEntity {
    private TestEntity test;
    private String text;
    private String variant1;
    private String variant2;
    private String variant3;
    private String variant4;
    private String image;
    public QuestionEntity() {
        super();
    }
    public QuestionEntity(Long id, TestEntity test, String text, String variant1, String variant2, String variant3,
            String variant4, String image) {
        super(id);
        this.test = test;
        this.text = text;
        this.variant1 = variant1;
        this.variant2 = variant2;
        this.variant3 = variant3;
        this.variant4 = variant4;
        this.image = image;
    }
    public void setTest(TestEntity test) {
        this.test = test;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setVariant1(String variant1) {
        this.variant1 = variant1;
    }
    public void setVariant2(String variant2) {
        this.variant2 = variant2;
    }
    public void setVariant3(String variant3) {
        this.variant3 = variant3;
    }
    public void setVariant4(String variant4) {
        this.variant4 = variant4;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public TestEntity getTest() {
        return test;
    }
    public String getText() {
        return text;
    }
    public String getVariant1() {
        return variant1;
    }
    public String getVariant2() {
        return variant2;
    }
    public String getVariant3() {
        return variant3;
    }
    public String getVariant4() {
        return variant4;
    }
    public String getImage() {
        return image;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((test == null) ? 0 : test.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        result = prime * result + ((variant1 == null) ? 0 : variant1.hashCode());
        result = prime * result + ((variant2 == null) ? 0 : variant2.hashCode());
        result = prime * result + ((variant3 == null) ? 0 : variant3.hashCode());
        result = prime * result + ((variant4 == null) ? 0 : variant4.hashCode());
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QuestionEntity other = (QuestionEntity) obj;
        if (test == null) {
            if (other.test != null)
                return false;
        } else if (!test.equals(other.test))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        if (variant1 == null) {
            if (other.variant1 != null)
                return false;
        } else if (!variant1.equals(other.variant1))
            return false;
        if (variant2 == null) {
            if (other.variant2 != null)
                return false;
        } else if (!variant2.equals(other.variant2))
            return false;
        if (variant3 == null) {
            if (other.variant3 != null)
                return false;
        } else if (!variant3.equals(other.variant3))
            return false;
        if (variant4 == null) {
            if (other.variant4 != null)
                return false;
        } else if (!variant4.equals(other.variant4))
            return false;
        if (image == null) {
            if (other.image != null)
                return false;
        } else if (!image.equals(other.image))
            return false;
        return true;
    }

}
