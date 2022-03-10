 insert into user (created_at, email, enabled, password, username) values (NOW(),'javierarriaga19011@gmail.com', true, '$2a$10$Cmjw1KiJifZjz0jz8cxEPu2BuY1wvnBglb8.kFWrh9rTFjZjRi0b2', 'RicardoSerrano');


insert into category (created_at, description, name, user_id) values (NOW(), 'Politica', 'Temas actuales de politica', 1);
insert into category (created_at, description, name, user_id) values (NOW(), 'Bitcoin', 'Que esta pasando con BTC', 1);


insert into post (category_id, created_at, description, name, url, user_id, votes) values (1, NOW(), 'La politica en la actualidad', 'rusiaVSucrania', '/rusiaVSucrania', 1, 0);

insert into post (category_id, created_at, description, name, url, user_id, votes) values (1, NOW(), 'La politica un tema de interes', 'politicainteresante', '/politicainteresante', 1, 0);

insert into comment (content, crated_at, post_id, user_id) values (1, NOW(), 1, 1);
