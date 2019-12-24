DROP TABLE IF EXISTS historico; 

CREATE TABLE historico (
  id_historico NUMERIC NOT NULL AUTO_INCREMENT,
  temperatura NUMERIC(6,2) NOT NULL,
  escala varchar(45) NOT NULL,
  resultado varchar(45) NOT NULL,
  data_consulta timestamp NOT NULL,
  UNIQUE KEY idHistorico_UNIQUE (id_historico)
);

