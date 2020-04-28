package com.fiipractic.fortech.foodtogo.constraint;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

   private String firstFieldName;
   private String secondFieldName;
   private String message;

   @Override
   public void initialize(final FieldMatch constraint) {
      firstFieldName = constraint.first();
      secondFieldName = constraint.second();
      message = constraint.message();
   }

   @Override
   public boolean isValid(Object value, ConstraintValidatorContext context) {

      boolean valid = true;
      try
      {
         final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
         final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

         valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
      }
      catch (final Exception ignore)
      {
         // ignore
      }

      if (!valid){
         context.buildConstraintViolationWithTemplate(message)
                 .addPropertyNode(firstFieldName)
                 .addConstraintViolation()
                 .disableDefaultConstraintViolation();
      }
      return valid;
   }
}
