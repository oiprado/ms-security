create table oauth_client_details (
    client_id VARCHAR(255) PRIMARY KEY,
    resource_ids VARCHAR(255),
    client_secret VARCHAR(255),
    scope VARCHAR(255),
    authorized_grant_types VARCHAR(255),
    web_server_redirect_uri VARCHAR(255),
    authorities VARCHAR(255),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(4096),
    autoapprove tinyint
);

create table oauth_client_token (
    token_id VARCHAR(255),
    token BLOB,
    authentication_id VARCHAR(255),
    user_name VARCHAR(255),
    client_id VARCHAR(255)
);

create table oauth_access_token (
    token_id VARCHAR(255),
    token BLOB,
    authentication_id VARCHAR(255),
    user_name VARCHAR(255),
    client_id VARCHAR(255),
    authentication BLOB,
    refresh_token VARCHAR(255)
);

create table oauth_refresh_token (
    token_id VARCHAR(255),
    token BLOB,
    authentication BLOB
);

create table oauth_code (
    code VARCHAR(255), authentication BLOB
);

create table user_info
(
    id INT(11) primary key auto_increment
);


alter table user_info
    ADD CONSTRAINT FK_user_info_user_account_id
        FOREIGN KEY (user_account_id) REFERENCES user_account(id);


insert into user_account(account_expired, create_date, credentials_expired, credentials_expired_date, enabled, locked, password, user_name)
values (FALSE, NOW(), FALSE, NULL, TRUE, FALSE, /*admin1234*/'$2a$08$qvrzQZ7jJ7oy2p/msL4M0.l83Cd0jNsX6AJUitbgRXGzge4j035ha', 'admin');

insert into user_account(account_expired, create_date, credentials_expired, credentials_expired_date, enabled, locked, password, user_name)
values (FALSE, NOW(), FALSE, NULL, TRUE, FALSE, /*reader1234*/'$2a$08$dwYz8O.qtUXboGosJFsS4u19LHKW7aCQ0LXXuNlRfjjGKwj5NfKSe', 'reader');


select * from user_account ua

create table sys_group
(
    id int(3) primary key auto_increment,
    name varchar(50) not null,
    description varchar(255),
    status bit
)

create table sys_role
(
    id int(3) primary key auto_increment,
    name varchar(50) not null,
    description varchar(255),
    status bit
)


create table role_user
(
    id bigint(20) primary key auto_increment,
    user_account_id bigint(20) not null,
    role_id int(3)
)

alter table role_user
    ADD CONSTRAINT FK_role_user_user_account_id
        FOREIGN KEY (user_account_id) REFERENCES user_account(id);

alter table role_user
    ADD CONSTRAINT FK_role_user_role_id
        FOREIGN KEY (role_id) REFERENCES role(id);

create table group_role
(
    id bigint(20) primary key auto_increment,
    user_account_id bigint(20) not null,
    group_id int(3)
)