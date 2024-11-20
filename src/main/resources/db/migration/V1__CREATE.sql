create table if not exists usuario(
    user_id bigint auto_increment,
    email varchar(255),
    nome varchar(255),
    primary key(user_id)
);

create table if not exists lampada(
    lampada_id bigint auto_increment,
    nome_dispositivo varchar(255),
    apelido varchar(255),
    estado varchar(255),
    modo varchar(255),
    user_id bigint,
    foreign key(user_id) references usuario(user_id),
    primary key(lampada_id)
);

create table if not exists consumo(
    consumo_id bigint auto_increment,
    consumo_wh float,
    mesConsumo datetime,
    lampada_id bigint,
    foreign key(lampada_id) references lampada(lampada_id),
    primary key(consumo_id)
);