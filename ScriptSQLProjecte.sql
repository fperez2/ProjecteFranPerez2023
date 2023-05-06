-- -----------------------------------------------------
-- Table Projecte2023.Persona
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Projecte2023.Persona (
  Persona_NIF VARCHAR(9) NOT NULL,
  Persona_Nom VARCHAR(45) NOT NULL,
  Persona_Cognom1 VARCHAR(45) NOT NULL,
  Persona_Cognom2 VARCHAR(45) NULL,
  Persona_DataNaix DATE NOT NULL,
  Persona_Adreca VARCHAR(45) NOT NULL,
  Persona_Poblacio VARCHAR(45) NOT NULL,
  Persona_Sexe CHAR(1) NOT NULL,
  Persona_Login VARCHAR(45) NOT NULL,
  Persona_Contraseña VARCHAR(45) NOT NULL,
  PRIMARY KEY (Persona_NIF),
  UNIQUE INDEX Persona_Login_UNIQUE (Persona_Login ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Projecte2023.Metge
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Projecte2023.Metge (
  Metge_NIF VARCHAR(9) NOT NULL,
  Metge_CodiEmpleat INT NOT NULL,
  PRIMARY KEY (Metge_NIF),
  INDEX fk_Metge_Persona_idx (Metge_NIF ASC) VISIBLE,
  UNIQUE INDEX Metge_CodiEmpleat_UNIQUE (Metge_CodiEmpleat ASC) VISIBLE,
  CONSTRAINT fk_Metge_Persona
    FOREIGN KEY (Metge_NIF)
    REFERENCES Projecte2023.Persona (Persona_NIF)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Projecte2023.Especialitat
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Projecte2023.Especialitat (
  Especialitat_Codi INT NOT NULL,
  Especialitat_Nom VARCHAR(45) NOT NULL,
  PRIMARY KEY (Especialitat_Codi),
  UNIQUE INDEX Especialitat_Nom_UNIQUE (Especialitat_Nom ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Projecte2023.Cita
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Projecte2023.Cita (
  Cita_Metge_CodiEmpleat INT NOT NULL,
  Cita_Persona_NIF VARCHAR(9) NOT NULL,
  Cita_DataHora TIMESTAMP NOT NULL,
  Cita_Informe VARCHAR(500) NULL,
  PRIMARY KEY (Cita_Metge_CodiEmpleat, Cita_Persona_NIF, Cita_DataHora),
  INDEX fk_Cita_Persona_idx (Cita_Persona_NIF ASC) VISIBLE,
  INDEX fk_Cita_Metge_idx (Cita_Metge_CodiEmpleat ASC) VISIBLE,
  CONSTRAINT fk_Cita_Persona
    FOREIGN KEY (Cita_Persona_NIF)
    REFERENCES Projecte2023.Persona (Persona_NIF)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Cita_Metge
    FOREIGN KEY (Cita_Metge_CodiEmpleat)
    REFERENCES Projecte2023.Metge (Metge_CodiEmpleat)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Projecte2023.MetgeEspecialitat
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Projecte2023.MetgeEspecialitat (
  Especialitat_Codi INT NOT NULL,
  Metge_CodiEmpleat INT NOT NULL,
  PRIMARY KEY (Especialitat_Codi, Metge_CodiEmpleat),
  INDEX fk_Especialitat_Metge_idx (Metge_CodiEmpleat ASC) VISIBLE,
  INDEX fk_Metge_Especialitat_idx (Especialitat_Codi ASC) VISIBLE,
  CONSTRAINT fk_Especialitat_Metge
    FOREIGN KEY (Especialitat_Codi)
    REFERENCES Projecte2023.Especialitat (Especialitat_Codi)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Metge_Especialitat
    FOREIGN KEY (Metge_CodiEmpleat)
    REFERENCES Projecte2023.Metge (Metge_CodiEmpleat)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Projecte2023.EntradaHorari
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Projecte2023.EntradaHorari (
  Metge_CodiEmpleat INT NOT NULL,
  EntradaHorari_Hora TIMESTAMP NOT NULL,
  EntradaHorari_DiaSetmana VARCHAR(45) NOT NULL,
  Especialitat_Codi INT NOT NULL,
  PRIMARY KEY (Metge_CodiEmpleat, EntradaHorari_Hora, EntradaHorari_DiaSetmana),
  INDEX fk_Metge_Esp_EntHor_idx (Especialitat_Codi ASC, Metge_CodiEmpleat ASC) VISIBLE,
  CONSTRAINT fk_Metge_Esp_EntHor
    FOREIGN KEY (Especialitat_Codi , Metge_CodiEmpleat)
    REFERENCES Projecte2023.MetgeEspecialitat (Especialitat_Codi , Metge_CodiEmpleat)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;