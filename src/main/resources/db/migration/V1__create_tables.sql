
    create sequence customer_seq start with 1 increment by 1;

    create sequence discount_seq start with 1 increment by 1;

    create sequence employee_seq start with 1 increment by 1;

    create sequence loyalty_program_seq start with 1 increment by 1;

    create sequence order_item_seq start with 1 increment by 1;

    create sequence order_seq start with 1 increment by 1;

    create sequence payment_seq start with 1 increment by 1;

    create sequence product_seq start with 1 increment by 1;

    create sequence stock_item_seq start with 1 increment by 1;

    create sequence stock_seq start with 1 increment by 1;

    create sequence unit_seq start with 1 increment by 1;

    create sequence user_seq start with 1 increment by 1;

    create table customer_tb (
        lgpd_consent boolean not null,
        created_at timestamp(6) not null,
        id bigint not null,
        lgpd_consent_date timestamp(6),
        loyalty_program_id bigint unique,
        user_id bigint not null unique,
        address varchar(255) not null,
        name varchar(255) not null,
        telephone varchar(255) not null,
        primary key (id)
    );

    create table discount_tb (
        discount_percentage numeric(38,2) not null,
        id bigint not null,
        valid_until timestamp(6) not null,
        name varchar(255) not null,
        product_id bigint unique not null,
        active boolean not null,
        primary key (id)
    );

    create table employee_tb (
        lgpd_consent boolean not null,
        created_at timestamp(6) not null,
        id bigint not null,
        lgpd_consent_date timestamp(6),
        unit_id bigint not null,
        user_id bigint not null unique,
        address varchar(255) not null,
        name varchar(255) not null,
        telephone varchar(255) not null,
        primary key (id)
    );

    create table loyalty_program_tb (
        loyalty_points integer not null,
        id bigint not null,
        primary key (id)
    );

    create table order_item_tb (
        item_subtotal numeric(38,2) not null,
        quantity integer not null,
        unit_price numeric(38,2) not null,
        discount_id bigint unique,
        id bigint not null,
        order_id bigint not null,
        product_id bigint not null,
        primary key (id)
    );

    create table order_tb (
        order_total numeric(38,2) not null,
        created_at timestamp(6) not null,
        customer_id bigint,
        id bigint not null,
        unit_id bigint not null,
        idempotency_key uuid not null unique,
        order_origin varchar(255) not null check ((order_origin in ('APP','COUNTER','PICKUP','TOTEM','WEBSITE'))),
        order_status varchar(255) not null check ((order_status in ('PAYMENT_PENDING','PREPARING','DELIVERED','CANCELLED'))),
        primary key (id)
    );

    create table payment_tb (
        created_at timestamp(6) not null,
        id bigint not null,
        order_id bigint not null unique,
        payment_status varchar(255) not null check ((payment_status in ('PENDING','APPROVED','REFUSED'))),
        payment_type varchar(255) not null check ((payment_type in ('MOCK_APPROVED', 'MOCK_REFUSED'))),
        primary key (id)
    );

    create table product_tb (
        unit_price numeric(38,2) not null,
        id bigint not null,
        name varchar(255) not null unique,
        active boolean not null,
        primary key (id)
    );

    create table stock_item_tb (
        id bigint not null,
        product_id bigint not null unique,
        quantity bigint not null,
        stock_id bigint not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table stock_tb (
        id bigint not null,
        unit_id bigint not null unique,
        primary key (id)
    );

    create table unit_tb (
        active boolean not null,
        id bigint not null,
        city varchar(255) not null,
        name varchar(255) not null,
        state varchar(255) not null,
        primary key (id)
    );

    create table user_tb (
        active boolean not null,
        created_at timestamp(6) not null,
        id bigint not null,
        email varchar(255) not null unique,
        password varchar(255) not null,
        role varchar(255) not null check ((role in ('ADMIN','UNIT_MANAGER','COUNTER_ATTENDANT','KITCHEN_ATTENDANT','CUSTOMER'))),
        primary key (id)
    );

    alter table if exists customer_tb 
       add constraint FKgpfj24qdydvq4m12gressaa36 
       foreign key (loyalty_program_id) 
       references loyalty_program_tb;

    alter table if exists customer_tb 
       add constraint FKi08xdhlb56owdwmqk5x1uyq63 
       foreign key (user_id) 
       references user_tb;

    alter table if exists discount_tb
        add constraint fk_discount_product
        foreign key  (product_id)
        references product_tb;

    alter table if exists employee_tb 
       add constraint FK90q4rmjetvu48kf4fyonk9g41 
       foreign key (unit_id) 
       references unit_tb;

    alter table if exists employee_tb 
       add constraint FKtm7we9fugafi22s2c6kbgv3sy 
       foreign key (user_id) 
       references user_tb;

    alter table if exists order_item_tb 
       add constraint FKhge7klqt8h3ij0kvxodwha28u 
       foreign key (discount_id) 
       references discount_tb;

    alter table if exists order_item_tb 
       add constraint FK9olaq53vi7eudwfvpvv3w7eey 
       foreign key (order_id) 
       references order_tb;

    alter table if exists order_item_tb 
       add constraint FKowmxam0wvvwik6jiisr60refj 
       foreign key (product_id) 
       references product_tb;

    alter table if exists order_tb 
       add constraint FK63okldqsuv2kdxqaqyoe309ee 
       foreign key (customer_id) 
       references customer_tb;

    alter table if exists order_tb 
       add constraint FKml986t8gox55pj950w4ceh0d6 
       foreign key (unit_id) 
       references unit_tb;

    alter table if exists payment_tb 
       add constraint FKmghf68sa00o27s0o8ry9p3lf 
       foreign key (order_id) 
       references order_tb;

    alter table if exists stock_item_tb 
       add constraint FKhqlm52f3h5w8b1pg1wa13of3t 
       foreign key (product_id) 
       references product_tb;

    alter table if exists stock_item_tb 
       add constraint FKb2gho03wue1cc7ttm5tegjhl2 
       foreign key (stock_id) 
       references stock_tb;

    alter table if exists stock_tb 
       add constraint FK3h80hy10ka1bw1akwl64hpt16 
       foreign key (unit_id) 
       references unit_tb;
