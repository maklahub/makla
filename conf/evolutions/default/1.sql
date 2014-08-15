# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table accounttype (
  id                        varchar(255) not null,
  reference                 varchar(255),
  name                      varchar(255),
  label                     varchar(255),
  create_time               timestamp not null,
  constraint pk_accounttype primary key (id))
;

create table address (
  id                        varchar(255) not null,
  address                   varchar(255),
  sub_address               varchar(255),
  city                      varchar(255),
  state                     varchar(255),
  zip                       varchar(255),
  country                   varchar(255),
  system_user_id            varchar(255),
  create_time               timestamp not null,
  constraint pk_address primary key (id))
;

create table albums (
  id                        varchar(255) not null,
  reference                 varchar(255),
  title                     varchar(255),
  description               TEXT,
  owner_id                  varchar(255),
  create_time               datetime,
  album_type                varchar(2),
  update_time               timestamp not null,
  constraint ck_albums_album_type check (album_type in ('o','p')),
  constraint pk_albums primary key (id))
;

create table carts (
  id                        varchar(255) not null,
  name                      varchar(255),
  requestor_id              varchar(255),
  provider_id               varchar(255),
  total_amount              double,
  tax_rate                  double,
  total_tax_amount          double,
  description               TEXT,
  create_time               datetime,
  close_time                datetime,
  update_time               timestamp not null,
  constraint pk_carts primary key (id))
;

create table cartitems (
  id                        varchar(255) not null,
  reference                 varchar(255),
  name                      varchar(255),
  cart_item_photo_id        varchar(255),
  description               TEXT,
  amount                    double,
  price                     double,
  quantity                  integer,
  cart_id                   varchar(255),
  owner_id                  varchar(255),
  create_time               datetime,
  close_time                datetime,
  test                      varchar(255),
  update_time               timestamp not null,
  constraint pk_cartitems primary key (id))
;

create table comments (
  id                        varchar(255) not null,
  commenter_id              varchar(255),
  description               TEXT,
  photo_id                  varchar(255),
  video_id                  varchar(255),
  create_time               timestamp,
  constraint pk_comments primary key (id))
;

create table feeds (
  id                        varchar(255) not null,
  system_user_id            varchar(255),
  url                       varchar(255),
  description               TEXT,
  create_time               timestamp not null,
  constraint pk_feeds primary key (id))
;

create table creditcards (
  id                        varchar(255) not null,
  name                      varchar(255),
  owner_id                  varchar(255),
  pay_pal_credit_card_id    varchar(255),
  description               TEXT,
  credit_card_number        varchar(255),
  create_time               datetime,
  close_time                datetime,
  update_time               timestamp not null,
  constraint pk_creditcards primary key (id))
;

create table menus (
  id                        varchar(255) not null,
  name                      varchar(255),
  owner_id                  varchar(255),
  menu_photo_id             varchar(255),
  description               TEXT,
  menu_type                 varchar(9),
  create_time               datetime,
  close_time                datetime,
  update_time               timestamp not null,
  constraint ck_menus_menu_type check (menu_type in ('Breakfast','Lunch','Dinner','Other')),
  constraint pk_menus primary key (id))
;

create table menuitems (
  id                        varchar(255) not null,
  reference                 varchar(255),
  name                      varchar(255),
  menu_item_photo_id        varchar(255),
  description               TEXT,
  price                     double,
  quantity                  integer,
  menu_id                   varchar(255),
  create_time               datetime,
  close_time                datetime,
  update_time               timestamp not null,
  constraint pk_menuitems primary key (id))
;

create table orders (
  id                        varchar(255) not null,
  reference                 varchar(255),
  name                      varchar(255),
  requestor_id              varchar(255),
  provider_id               varchar(255),
  description               TEXT,
  total_amount              double,
  address_id                varchar(255),
  status                    varchar(10),
  tax_rate                  double,
  delivery                  double,
  total_tax_amount          double,
  delivery_time             datetime,
  create_time               datetime,
  close_time                datetime,
  update_time               timestamp not null,
  constraint ck_orders_status check (status in ('Draft','Precessing','Paid','Confirmed','Complete')),
  constraint pk_orders primary key (id))
;

create table orderitems (
  id                        varchar(255) not null,
  reference                 varchar(255),
  name                      varchar(255),
  description               TEXT,
  price                     double,
  amount                    double,
  quantity                  integer,
  order_id                  varchar(255),
  order_item_photo_id       varchar(255),
  create_time               datetime,
  close_time                datetime,
  update_time               timestamp not null,
  constraint pk_orderitems primary key (id))
;

create table organizations (
  id                        varchar(255) not null,
  name                      varchar(255),
  email                     varchar(255),
  www                       varchar(255),
  phone_number              varchar(255),
  category_id               varchar(255),
  address_id                varchar(255),
  billing_address_id        varchar(255),
  shipping_address_id       varchar(255),
  create_time               datetime,
  update_time               timestamp not null,
  constraint pk_organizations primary key (id))
;

create table organizationcategories (
  id                        varchar(255) not null,
  reference                 varchar(255),
  name                      varchar(255),
  label                     varchar(255),
  description               TEXT,
  create_time               timestamp not null,
  constraint pk_organizationcategories primary key (id))
;

create table persons (
  id                        varchar(255) not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  www                       varchar(255),
  phone_number              varchar(255),
  cell                      varchar(255),
  birth_date                datetime,
  gender                    varchar(6),
  category_id               varchar(255),
  create_time               datetime,
  update_time               timestamp not null,
  constraint ck_persons_gender check (gender in ('Male','Female','Other')),
  constraint pk_persons primary key (id))
;

create table personcategories (
  id                        varchar(255) not null,
  reference                 varchar(255),
  name                      varchar(255),
  label                     varchar(255),
  description               TEXT,
  create_time               datetime,
  update_time               timestamp not null,
  constraint pk_personcategories primary key (id))
;

create table photos (
  id                        varchar(255) not null,
  reference                 varchar(255),
  title                     varchar(255),
  description               TEXT,
  file_name                 varchar(255),
  url                       varchar(255),
  document_type             varchar(255),
  file_size                 bigint,
  key_words                 varchar(255),
  extension                 varchar(255),
  album_id                  varchar(255),
  owner_id                  varchar(255),
  create_time               datetime,
  status                    varchar(2),
  update_time               timestamp not null,
  constraint ck_photos_status check (status in ('i','a')),
  constraint pk_photos primary key (id))
;

create table s3file (
  id                        varchar(40) not null,
  bucket                    varchar(255),
  name                      varchar(255),
  constraint pk_s3file primary key (id))
;

create table systemaccounts (
  id                        varchar(255) not null,
  name                      varchar(255),
  description               TEXT,
  system_user_id            varchar(255),
  account_email             varchar(255),
  account_password          varchar(255),
  account_type              varchar(8),
  create_time               datetime,
  update_time               timestamp not null,
  constraint ck_systemaccounts_account_type check (account_type in ('free','gold','platinum')),
  constraint pk_systemaccounts primary key (id))
;

create table systemusers (
  id                        varchar(255) not null,
  person_id                 varchar(255),
  main_credit_card_id       varchar(255),
  user_name                 varchar(255),
  organization_id           varchar(255),
  user_type_id              varchar(255),
  profile_image_id          varchar(255),
  profile_image_url         varchar(255),
  address_id                varchar(255),
  billing_address_id        varchar(255),
  create_time               datetime,
  update_time               timestamp not null,
  constraint pk_systemusers primary key (id))
;

create table usertypes (
  id                        varchar(255) not null,
  reference                 varchar(255),
  name                      varchar(255),
  label                     varchar(255),
  description               TEXT,
  create_time               timestamp not null,
  constraint pk_usertypes primary key (id))
;

create table videos (
  id                        varchar(255) not null,
  reference                 varchar(255),
  title                     varchar(255),
  description               TEXT,
  file_name                 varchar(255),
  url                       varchar(255),
  document_type             varchar(255),
  file_size                 bigint,
  key_words                 varchar(255),
  extension                 varchar(255),
  album_id                  varchar(255),
  owner_id                  varchar(255),
  create_time               datetime,
  status                    varchar(2),
  update_time               timestamp not null,
  constraint ck_videos_status check (status in ('i','a')),
  constraint pk_videos primary key (id))
;


create table carts_cartitems (
  carts_id                       varchar(255) not null,
  cartitems_id                   varchar(255) not null,
  constraint pk_carts_cartitems primary key (carts_id, cartitems_id))
;

create table menus_menuitems (
  menus_id                       varchar(255) not null,
  menuitems_id                   varchar(255) not null,
  constraint pk_menus_menuitems primary key (menus_id, menuitems_id))
;

create table orders_orderitems (
  orders_id                      varchar(255) not null,
  orderitems_id                  varchar(255) not null,
  constraint pk_orders_orderitems primary key (orders_id, orderitems_id))
;

create table organizations_organizationcategories (
  organizations_id               varchar(255) not null,
  organizationcategories_id      varchar(255) not null,
  constraint pk_organizations_organizationcategories primary key (organizations_id, organizationcategories_id))
;

create table persons_personcategories (
  persons_id                     varchar(255) not null,
  personcategories_id            varchar(255) not null,
  constraint pk_persons_personcategories primary key (persons_id, personcategories_id))
;

create table persons_organizations (
  persons_id                     varchar(255) not null,
  organizations_id               varchar(255) not null,
  constraint pk_persons_organizations primary key (persons_id, organizations_id))
;

create table systemusers_address (
  systemusers_id                 varchar(255) not null,
  address_id                     varchar(255) not null,
  constraint pk_systemusers_address primary key (systemusers_id, address_id))
;
alter table address add constraint fk_address_systemUser_1 foreign key (system_user_id) references systemusers (id) on delete restrict on update restrict;
create index ix_address_systemUser_1 on address (system_user_id);
alter table albums add constraint fk_albums_owner_2 foreign key (owner_id) references systemusers (id) on delete restrict on update restrict;
create index ix_albums_owner_2 on albums (owner_id);
alter table carts add constraint fk_carts_requestor_3 foreign key (requestor_id) references systemusers (id) on delete restrict on update restrict;
create index ix_carts_requestor_3 on carts (requestor_id);
alter table carts add constraint fk_carts_provider_4 foreign key (provider_id) references systemusers (id) on delete restrict on update restrict;
create index ix_carts_provider_4 on carts (provider_id);
alter table cartitems add constraint fk_cartitems_cartItemPhoto_5 foreign key (cart_item_photo_id) references photos (id) on delete restrict on update restrict;
create index ix_cartitems_cartItemPhoto_5 on cartitems (cart_item_photo_id);
alter table cartitems add constraint fk_cartitems_cart_6 foreign key (cart_id) references carts (id) on delete restrict on update restrict;
create index ix_cartitems_cart_6 on cartitems (cart_id);
alter table cartitems add constraint fk_cartitems_owner_7 foreign key (owner_id) references systemusers (id) on delete restrict on update restrict;
create index ix_cartitems_owner_7 on cartitems (owner_id);
alter table comments add constraint fk_comments_commenter_8 foreign key (commenter_id) references systemusers (id) on delete restrict on update restrict;
create index ix_comments_commenter_8 on comments (commenter_id);
alter table comments add constraint fk_comments_photo_9 foreign key (photo_id) references photos (id) on delete restrict on update restrict;
create index ix_comments_photo_9 on comments (photo_id);
alter table comments add constraint fk_comments_video_10 foreign key (video_id) references videos (id) on delete restrict on update restrict;
create index ix_comments_video_10 on comments (video_id);
alter table feeds add constraint fk_feeds_systemUser_11 foreign key (system_user_id) references systemusers (id) on delete restrict on update restrict;
create index ix_feeds_systemUser_11 on feeds (system_user_id);
alter table creditcards add constraint fk_creditcards_owner_12 foreign key (owner_id) references systemusers (id) on delete restrict on update restrict;
create index ix_creditcards_owner_12 on creditcards (owner_id);
alter table menus add constraint fk_menus_owner_13 foreign key (owner_id) references systemusers (id) on delete restrict on update restrict;
create index ix_menus_owner_13 on menus (owner_id);
alter table menus add constraint fk_menus_menuPhoto_14 foreign key (menu_photo_id) references photos (id) on delete restrict on update restrict;
create index ix_menus_menuPhoto_14 on menus (menu_photo_id);
alter table menuitems add constraint fk_menuitems_menuItemPhoto_15 foreign key (menu_item_photo_id) references photos (id) on delete restrict on update restrict;
create index ix_menuitems_menuItemPhoto_15 on menuitems (menu_item_photo_id);
alter table menuitems add constraint fk_menuitems_menu_16 foreign key (menu_id) references menus (id) on delete restrict on update restrict;
create index ix_menuitems_menu_16 on menuitems (menu_id);
alter table orders add constraint fk_orders_requestor_17 foreign key (requestor_id) references systemusers (id) on delete restrict on update restrict;
create index ix_orders_requestor_17 on orders (requestor_id);
alter table orders add constraint fk_orders_provider_18 foreign key (provider_id) references systemusers (id) on delete restrict on update restrict;
create index ix_orders_provider_18 on orders (provider_id);
alter table orders add constraint fk_orders_address_19 foreign key (address_id) references address (id) on delete restrict on update restrict;
create index ix_orders_address_19 on orders (address_id);
alter table orderitems add constraint fk_orderitems_order_20 foreign key (order_id) references orders (id) on delete restrict on update restrict;
create index ix_orderitems_order_20 on orderitems (order_id);
alter table orderitems add constraint fk_orderitems_orderItemPhoto_21 foreign key (order_item_photo_id) references photos (id) on delete restrict on update restrict;
create index ix_orderitems_orderItemPhoto_21 on orderitems (order_item_photo_id);
alter table organizations add constraint fk_organizations_category_22 foreign key (category_id) references organizationcategories (id) on delete restrict on update restrict;
create index ix_organizations_category_22 on organizations (category_id);
alter table organizations add constraint fk_organizations_address_23 foreign key (address_id) references address (id) on delete restrict on update restrict;
create index ix_organizations_address_23 on organizations (address_id);
alter table organizations add constraint fk_organizations_billingAddress_24 foreign key (billing_address_id) references address (id) on delete restrict on update restrict;
create index ix_organizations_billingAddress_24 on organizations (billing_address_id);
alter table organizations add constraint fk_organizations_shippingAddress_25 foreign key (shipping_address_id) references address (id) on delete restrict on update restrict;
create index ix_organizations_shippingAddress_25 on organizations (shipping_address_id);
alter table persons add constraint fk_persons_category_26 foreign key (category_id) references personcategories (id) on delete restrict on update restrict;
create index ix_persons_category_26 on persons (category_id);
alter table photos add constraint fk_photos_album_27 foreign key (album_id) references albums (id) on delete restrict on update restrict;
create index ix_photos_album_27 on photos (album_id);
alter table photos add constraint fk_photos_owner_28 foreign key (owner_id) references systemusers (id) on delete restrict on update restrict;
create index ix_photos_owner_28 on photos (owner_id);
alter table systemaccounts add constraint fk_systemaccounts_systemUser_29 foreign key (system_user_id) references systemusers (id) on delete restrict on update restrict;
create index ix_systemaccounts_systemUser_29 on systemaccounts (system_user_id);
alter table systemusers add constraint fk_systemusers_person_30 foreign key (person_id) references persons (id) on delete restrict on update restrict;
create index ix_systemusers_person_30 on systemusers (person_id);
alter table systemusers add constraint fk_systemusers_mainCreditCard_31 foreign key (main_credit_card_id) references creditcards (id) on delete restrict on update restrict;
create index ix_systemusers_mainCreditCard_31 on systemusers (main_credit_card_id);
alter table systemusers add constraint fk_systemusers_organization_32 foreign key (organization_id) references organizations (id) on delete restrict on update restrict;
create index ix_systemusers_organization_32 on systemusers (organization_id);
alter table systemusers add constraint fk_systemusers_userType_33 foreign key (user_type_id) references usertypes (id) on delete restrict on update restrict;
create index ix_systemusers_userType_33 on systemusers (user_type_id);
alter table systemusers add constraint fk_systemusers_address_34 foreign key (address_id) references address (id) on delete restrict on update restrict;
create index ix_systemusers_address_34 on systemusers (address_id);
alter table systemusers add constraint fk_systemusers_billingAddress_35 foreign key (billing_address_id) references address (id) on delete restrict on update restrict;
create index ix_systemusers_billingAddress_35 on systemusers (billing_address_id);
alter table videos add constraint fk_videos_album_36 foreign key (album_id) references albums (id) on delete restrict on update restrict;
create index ix_videos_album_36 on videos (album_id);
alter table videos add constraint fk_videos_owner_37 foreign key (owner_id) references systemusers (id) on delete restrict on update restrict;
create index ix_videos_owner_37 on videos (owner_id);



alter table carts_cartitems add constraint fk_carts_cartitems_carts_01 foreign key (carts_id) references carts (id) on delete restrict on update restrict;

alter table carts_cartitems add constraint fk_carts_cartitems_cartitems_02 foreign key (cartitems_id) references cartitems (id) on delete restrict on update restrict;

alter table menus_menuitems add constraint fk_menus_menuitems_menus_01 foreign key (menus_id) references menus (id) on delete restrict on update restrict;

alter table menus_menuitems add constraint fk_menus_menuitems_menuitems_02 foreign key (menuitems_id) references menuitems (id) on delete restrict on update restrict;

alter table orders_orderitems add constraint fk_orders_orderitems_orders_01 foreign key (orders_id) references orders (id) on delete restrict on update restrict;

alter table orders_orderitems add constraint fk_orders_orderitems_orderitems_02 foreign key (orderitems_id) references orderitems (id) on delete restrict on update restrict;

alter table organizations_organizationcategories add constraint fk_organizations_organizationcategories_organizations_01 foreign key (organizations_id) references organizations (id) on delete restrict on update restrict;

alter table organizations_organizationcategories add constraint fk_organizations_organizationcategories_organizationcategorie_02 foreign key (organizationcategories_id) references organizationcategories (id) on delete restrict on update restrict;

alter table persons_personcategories add constraint fk_persons_personcategories_persons_01 foreign key (persons_id) references persons (id) on delete restrict on update restrict;

alter table persons_personcategories add constraint fk_persons_personcategories_personcategories_02 foreign key (personcategories_id) references personcategories (id) on delete restrict on update restrict;

alter table persons_organizations add constraint fk_persons_organizations_persons_01 foreign key (persons_id) references persons (id) on delete restrict on update restrict;

alter table persons_organizations add constraint fk_persons_organizations_organizations_02 foreign key (organizations_id) references organizations (id) on delete restrict on update restrict;

alter table systemusers_address add constraint fk_systemusers_address_systemusers_01 foreign key (systemusers_id) references systemusers (id) on delete restrict on update restrict;

alter table systemusers_address add constraint fk_systemusers_address_address_02 foreign key (address_id) references address (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table accounttype;

drop table address;

drop table albums;

drop table carts;

drop table carts_cartitems;

drop table cartitems;

drop table comments;

drop table feeds;

drop table creditcards;

drop table menus;

drop table menus_menuitems;

drop table menuitems;

drop table orders;

drop table orders_orderitems;

drop table orderitems;

drop table organizations;

drop table persons_organizations;

drop table organizations_organizationcategories;

drop table organizationcategories;

drop table persons;

drop table persons_personcategories;

drop table personcategories;

drop table photos;

drop table s3file;

drop table systemaccounts;

drop table systemusers;

drop table systemusers_address;

drop table usertypes;

drop table videos;

SET FOREIGN_KEY_CHECKS=1;

