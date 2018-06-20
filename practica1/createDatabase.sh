mysql -uroot -e "
  drop user if exists 'grupo5'@'localhost';  create user 'grupo5'@'localhost';
  drop database if exists bd2_grupo5; create database bd2_grupo5;
  grant all privileges on bd2_grupo5.* to 'grupo5'@'localhost';
"
