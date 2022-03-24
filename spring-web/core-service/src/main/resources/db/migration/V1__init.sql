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
    price         numeric (8, 2) not null,
    category_id   bigint not null references categories (id),
    created_at    timestamp default current_timestamp,
    updated_at    timestamp default current_timestamp
);

insert into categories (title)
values ('Vegetables'),
       ('Fruit');

insert into products (title, price, category_id)
values ('Potato', 2.00, 1),
       ('Carrot', 4.00, 1),
       ('Pepper', 8.00, 1),
       ('Tomato', 16.00, 1),
       ('Asparagus', 75.00, 1),
       ('Apple', 23.00, 2),
       ('Beet', 12.00, 1),
       ('Orange', 45.00, 2),
       ('Cabbage', 21.00, 1),
       ('Grape', 47.00, 2),
       ('Celery', 38.00, 1),
       ('Corn', 33.00, 1),
       ('Cucumber', 145.00, 1),
       ('Daikon', 97.00, 1),
       ('Eggplant', 123.00, 1),
       ('Garlic', 67.00, 1),
       ('Kale', 77.00, 1),
       ('Lettuce', 68.00, 1),
       ('Onion', 25.00, 1),
       ('Parsley', 27.00, 1);

create table orders
(
    id          bigserial primary key,
    username    varchar (255) not null,
    total_price numeric (8, 2) not null,
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
    price_per_product numeric (8, 2) not null,
    price             numeric (8, 2) not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);










