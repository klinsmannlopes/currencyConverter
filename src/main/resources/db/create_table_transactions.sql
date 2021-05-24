create table transactions (
                                id serial primary key,
                                user_code varchar not null,
                                origin_currency varchar not null,
                                origin_value numeric not null,
                                destiny_currency varchar not null,
                                destiny_value numeric not null,
                                rate_used numeric not null,
                                creation_date timestamp
);