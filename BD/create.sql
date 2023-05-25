
-- -----------------------------------------------------
-- Table projecte2023.persona
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS persona (
  Persona_NIF VARCHAR(9) NOT NULL,
  Persona_Nom VARCHAR(45) NOT NULL,
  Persona_Cognom1 VARCHAR(45) NOT NULL,
  Persona_Cognom2 VARCHAR(45) NULL DEFAULT NULL,
  Persona_DataNaix DATE NOT NULL,
  Persona_Adreca VARCHAR(45) NOT NULL,
  Persona_Poblacio VARCHAR(45) NOT NULL,
  Persona_Sexe CHAR(1) NOT NULL,
  Persona_Login VARCHAR(45) NOT NULL,
  Persona_Password VARCHAR(45) NOT NULL,
  PRIMARY KEY (Persona_NIF),
  UNIQUE INDEX Persona_Login_UNIQUE (Persona_Login ASC));



-- -----------------------------------------------------
-- Table projecte2023.metge
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS metge (
  Metge_Persona_NIF VARCHAR(9) NOT NULL,
  Metge_CodiEmpleat INT NOT NULL,
  PRIMARY KEY (Metge_CodiEmpleat),
  UNIQUE INDEX Metge_NIF_UNIQUE (Metge_Persona_NIF ASC),
  INDEX fk_Metge_Persona_idx (Metge_Persona_NIF ASC),
  CONSTRAINT fk_Metge_Persona
    FOREIGN KEY (Metge_Persona_NIF)
    REFERENCES persona (Persona_NIF));


-- -----------------------------------------------------
-- Table projecte2023.cita
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS cita (
  Cita_Metge_CodiEmpleat INT NOT NULL,
  Cita_Persona_NIF VARCHAR(9) NOT NULL,
  Cita_DataHora TIMESTAMP NOT NULL,
  Cita_Informe VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (Cita_Metge_CodiEmpleat, Cita_Persona_NIF, Cita_DataHora),
  INDEX fk_Cita_Persona_idx (Cita_Persona_NIF ASC),
  INDEX fk_Cita_Metge_idx (Cita_Metge_CodiEmpleat ASC),
  CONSTRAINT fk_Cita_Metge
    FOREIGN KEY (Cita_Metge_CodiEmpleat)
    REFERENCES metge (Metge_CodiEmpleat),
  CONSTRAINT fk_Cita_Persona
    FOREIGN KEY (Cita_Persona_NIF)
    REFERENCES persona (Persona_NIF));


-- -----------------------------------------------------
-- Table projecte2023.especialitat
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS especialitat (
  Especialitat_Codi INT NOT NULL,
  Especialitat_Nom VARCHAR(45) NOT NULL,
  PRIMARY KEY (Especialitat_Codi),
  UNIQUE INDEX Especialitat_Nom_UNIQUE (Especialitat_Nom ASC));


-- -----------------------------------------------------
-- Table projecte2023.metgeespecialitat
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS metgeespecialitat (
  Especialitat_Codi INT NOT NULL,
  Metge_CodiEmpleat INT NOT NULL,
  PRIMARY KEY (Especialitat_Codi, Metge_CodiEmpleat),
  INDEX fk_Especialitat_Metge_idx (Metge_CodiEmpleat ASC),
  INDEX fk_Metge_Especialitat_idx (Especialitat_Codi ASC),
  CONSTRAINT fk_Especialitat_Metge
    FOREIGN KEY (Especialitat_Codi)
    REFERENCES especialitat (Especialitat_Codi),
  CONSTRAINT fk_Metge_Especialitat
    FOREIGN KEY (Metge_CodiEmpleat)
    REFERENCES metge (Metge_CodiEmpleat));


-- -----------------------------------------------------
-- Table projecte2023.entradahorari
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS entradahorari (
  Metge_CodiEmpleat INT NOT NULL,
  EntradaHorari_Hora TIMESTAMP NOT NULL,
  EntradaHorari_DiaSetmana VARCHAR(45) NOT NULL,
  Especialitat_Codi INT NOT NULL,
  PRIMARY KEY (Metge_CodiEmpleat, EntradaHorari_Hora, EntradaHorari_DiaSetmana),
  INDEX fk_Metge_Esp_EntHor (Especialitat_Codi ASC, Metge_CodiEmpleat ASC),
  CONSTRAINT fk_Metge_Esp_EntHor
    FOREIGN KEY (Especialitat_Codi , Metge_CodiEmpleat)
    REFERENCES metgeespecialitat (Especialitat_Codi , Metge_CodiEmpleat));
