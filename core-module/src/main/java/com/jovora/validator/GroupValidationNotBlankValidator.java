package com.jovora.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class GroupValidationNotBlankValidator implements ConstraintValidator<GroupValidationNotBlank, Object> {

    private String[] fields;

    public void initialize(GroupValidationNotBlank constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean hasNullValue = false;
        boolean hasNonNullValue = false;
        boolean isValid = false;

        for (String field : fields) {
            Object propertyValue = new BeanWrapperImpl(value).getPropertyValue(field);
            if (ObjectUtils.isEmpty(propertyValue) || !StringUtils.hasText(propertyValue.toString())) {
                hasNullValue = true;
            } else {
                hasNonNullValue = true;
            }

            if (hasNullValue && hasNonNullValue) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}