package com.example.kursovoi;

public class Work {
    String str;

    /**
     *
     * @param value  Строка данных для
     */
    public void load(String value){
        str=value;
    }

   public String[] splitter(){
try{
       String[] result=new String[8];
       String string=str.replace('—','-');
      String temp;
       string=string.substring(string.indexOf(' '));
       //===============Название книги=======================
       result[0]=string.substring(string.indexOf(' '),string.indexOf(':'));
       result[0]=result[0].trim();
       //==============Жанр==================================
       result[1]=string.substring(string.indexOf(':')+1,string.indexOf('/'));
       if(result[1].contains("[")) {
           result[1] = string.substring(string.indexOf('[') + 1, string.indexOf(']'));
       }
       result[1]=result[1].trim();
       //==============Автор==================================
       result[2]=string.substring(string.indexOf('/')+1,string.indexOf('-')).trim();
       if(result[2].contains(".")){
           result[2]=result[2].substring(0,result[2].indexOf('.')).trim();
       }
       result[2]=result[2].trim();
       //==============Город==================================
       temp=string.substring(string.indexOf('-')+1);
       temp=temp.trim();
       //=====================================================
       result[3]=temp.substring(0,temp.indexOf(':'));
       result[3]=result[3].trim();
       temp=temp.substring(temp.indexOf(':')+1);
       //==============Издательство==================================
       result[4]=temp.substring(0,temp.indexOf(','));
       result[4]=result[4].trim();
       temp=temp.substring(temp.indexOf(',')+1);
       temp=temp.trim();
       //==============Год==================================
       result[5]=temp.substring(0,temp.indexOf(' ')-1);
       result[5]=result[5].trim();
       temp=temp.substring(temp.indexOf('.')+1);
       temp=temp.trim();
       //==============Кол-во страниц==================================
       result[6]=temp.substring(temp.indexOf('-')+1,temp.indexOf('с'));
       if(result[6].contains("[")) {
           result[6] = result[6].substring(0,result[6].indexOf('['));
       }
       result[6]=result[6].replaceAll("\\D+","");
       result[6]=result[6].trim();
       //==============ISBN==================================
       result[7]=temp.substring(temp.indexOf('9'));
       result[7]=result[7].trim();
       return  result;
}
catch (IllegalStateException ex){
return  null;
}
catch (StringIndexOutOfBoundsException ex){
    return null;
}
   }
}
