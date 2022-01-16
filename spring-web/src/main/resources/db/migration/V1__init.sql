create table products
(
    id         bigserial primary key,
    title      varchar(255),
    price      int,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into products (title, price)
values ('Potato', 2),
       ('Carrot', 4),
       ('Pepper', 8),
       ('Tomato', 16),
       ('Asparagus', 75),
       ('Beans', 23),
       ('Beet', 12),
       ('Broccoli', 45),
       ('Cabbage ', 21),
       ('Cauliflower ', 47),
       ('Celery ', 38),
       ('Corn', 33),
       ('Cucumber ', 145),
       ('Daikon', 97),
       ('Eggplant', 123),
       ('Garlic', 67),
       ('Kale ', 77),
       ('Lettuce ', 68),
       ('Onion ', 25),
       ('Parsley', 27);

create table users
(
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table users_roles
(
    user_id    bigint not null references users (id),
    role_id    bigint not null references roles (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (user_id, role_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('MyUser', '$2a$12$QcqtrvJhZNtmUI5u/QRrfOpGfaMubI5ZiXahUYk5TUppxIHT8Vt4C', 'myuser@gmail.com'),
       ('MyAdmin', '$2a$12$QcqtrvJhZNtmUI5u/QRrfOpGfaMubI5ZiXahUYk5TUppxIHT8Vt4C', 'myadmin@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);

create table orders
(
    id          bigserial primary key,
    user_id     bigint not null references users (id),
    total_price int    not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price_per_product int    not null,
    price             int    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

insert into orders (user_id, total_price, address, phone)
values (1, 4, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price)
values (1, 1, 2, 2, 4);









