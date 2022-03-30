create table categories
(
    id          bigserial primary key,
    title       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table products
(
    id            bigserial primary key,
    title         varchar(255),
    price         int,
    category_id   bigint not null references categories (id),
    created_at    timestamp default current_timestamp,
    updated_at    timestamp default current_timestamp
);

insert into categories (title)
values ('Vegetables'),
       ('Fruit');

insert into products (title, price, category_id)
values ('Potato', 2, 1),
       ('Carrot', 4, 1),
       ('Pepper', 8, 1),
       ('Tomato', 16, 1),
       ('Asparagus', 75, 1),
       ('Apple', 23, 2),
       ('Beet', 12, 1),
       ('Orange', 45, 2),
       ('Cabbage', 21, 1),
       ('Grape', 47, 2),
       ('Celery', 38, 1),
       ('Corn', 33, 1),
       ('Cucumber', 145, 1),
       ('Daikon', 97, 1),
       ('Eggplant', 123, 1),
       ('Garlic', 67, 1),
       ('Kale', 77, 1),
       ('Lettuce', 68, 1),
       ('Onion', 25, 1),
       ('Parsley', 27, 1);

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








