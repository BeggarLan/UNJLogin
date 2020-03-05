function getEncryptPassword(username,password,lt )      { //注意，参数不要带var。。在java里执行会报错。。


      var enPassword = strEnc(username+password+lt , '1' , '2' , '3');
      return enPassword;

}