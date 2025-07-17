ALTER TABLE cliente ADD CONSTRAINT dni_unique UNIQUE(dni);
INSERT INTO producto (nombre) VALUES('PZOF');
INSERT INTO producto (nombre) VALUES('CHEQ');
INSERT INTO producto (nombre) VALUES('TJCREDITO');
INSERT INTO producto (nombre) VALUES('TJDEBITO');


INSERT INTO usuario(username, password) VALUES
('user1', '$2a$10$o5w5OaeOxfm2TGr9z28TtOM4AvcDG96KGMxa3gHUd7l87VLSkUp8G' ),
('user2', '$2a$10$BlSl/jDsP.hg4GfG9jS57ue4hHglVEFZRADZezLr1B59vBrE3B7Oa');