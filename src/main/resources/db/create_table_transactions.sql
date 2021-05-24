create table transactions (
                                id serial primary key,
                                idUser varchar not null,
                                originCurrency varchar not null,
                                originValue numeric not null,
                                destinyCurrency varchar not null,
                                destinyValue numeric not null,
                                rateUsed numeric not null,
                                creationDate timestamp
);