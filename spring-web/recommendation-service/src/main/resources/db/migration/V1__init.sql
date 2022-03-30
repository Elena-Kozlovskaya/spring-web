create table recommendation_items
(
    id                bigserial primary key,
    product_id        int    not null,
    product_title     varchar(255),
    quantity          int    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

insert into recommendation_items(product_id, product_title, quantity)
values(1, 'Potato', 10);









