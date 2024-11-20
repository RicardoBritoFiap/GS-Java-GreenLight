insert into usuario(email, nome) values('pedrodentocohd@gmail.com', 'pedro');

insert into lampada(nome_dispositivo, apelido, estado, modo, user_id) values('GreenLightLamp1', 'sala de estar', 'apagada', 'automatico', 1);
insert into lampada(nome_dispositivo, apelido, estado, modo, user_id) values('GreenLightLamp2', 'quintal', 'apagada', 'automatico', 1);
insert into lampada(nome_dispositivo, apelido, estado, modo, user_id) values('GreenLightLamp3', 'quarto1', 'apagada', 'automatico', 1);

insert into consumo(consumo_wh, mes_consumo, lampada_id) values(23.10, '2024-01-30 00:00:00', 1);
insert into consumo(consumo_wh, mes_consumo, lampada_id) values(54.20, '2024-02-30 00:00:00', 1);