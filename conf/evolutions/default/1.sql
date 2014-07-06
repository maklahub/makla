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
  city                      varchar(255),
  state                     varchar(255),
  zip                       varchar(255),
  country                   varchar(255),
  create_time               timestamp not null,
  constraint pk_address primary key (id))
;

create table albums (
  id                        varchar(255) not null,
  reference                 varchar(255),
  title                     varchar(255),
  description               varchar(255),
  owner_id                  varchar(255),
  create_time               datetime,
  album_type                varchar(2),
  update_time               timestamp not null,
  constraint ck_albums_album_type check (album_type in ('o','p')),
  constraint pk_albums primary key (id))
;

create table comments (
  id                        varchar(255) not null,
  commenter_id              varchar(255),
  description               varchar(255),
  photo_id                  varchar(255),
  video_id                  varchar(255),
  create_time               timestamp,
  constraint pk_comments primary key (id))
;

create table feeds (
  id                        varchar(255) not null,
  system_user_id            varchar(255),
  url                       varchar(255),
  description               varchar(255),
  create_time               timestamp not null,
  constraint pk_feeds primary key (id))
;

create table menus (
  id                        varchar(255) not null,
  name                      varchar(255),
  owner_id                  varchar(255),
  description               varchar(255),
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
  description               varchar(255),
  price                     double,
  quantity                  integer,
  menu_id                   varchar(255),
  create_time               datetime,
  close_time                datetime,
  update_time               timestamp not null,
  constraint pk_menuitems primary key (id))
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
  address_id                varchar(255),
  billing_address_id        varchar(255),
  shipping_address_id       varchar(255),
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
  create_time               datetime,
  update_time               timestamp not null,
  constraint pk_personcategories primary key (id))
;

create table photos (
  id                        varchar(255) not null,
  reference                 varchar(255),
  title                     varchar(255),
  description               varchar(255),
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
  user_name                 varchar(255),
  organization_id           varchar(255),
  user_type_id              varchar(255),
  profile_image_id          varchar(255),
  profile_image_url         varchar(255),
  create_time               datetime,
  update_time               timestamp not null,
  constraint pk_systemusers primary key (id))
;

create table usertypes (
  id                        varchar(255) not null,
  reference                 varchar(255),
  name                      varchar(255),
  label                     varchar(255),
  create_time               timestamp not null,
  constraint pk_usertypes primary key (id))
;

create table videos (
  id                        varchar(255) not null,
  reference                 varchar(255),
  title                     varchar(255),
  description               varchar(255),
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


create table menus_menuitems (
  menus_id                       varchar(255) not null,
  menuitems_id                   varchar(255) not null,
  constraint pk_menus_menuitems primary key (menus_id, menuitems_id))
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
alter table albums add constraint fk_albums_owner_1 foreign key (owner_id) references systemusers (id) on delete restrict on update restrict;
create index ix_albums_owner_1 on albums (owner_id);
alter table comments add constraint fk_comments_commenter_2 foreign key (commenter_id) references systemusers (id) on delete restrict on update restrict;
create index ix_comments_commenter_2 on comments (commenter_id);
alter table comments add constraint fk_comments_photo_3 foreign key (photo_id) references photos (id) on delete restrict on update restrict;
create index ix_comments_photo_3 on comments (photo_id);
alter table comments add constraint fk_comments_video_4 foreign key (video_id) references videos (id) on delete restrict on update restrict;
create index ix_comments_video_4 on comments (video_id);
alter table feeds add constraint fk_feeds_systemUser_5 foreign key (system_user_id) references systemusers (id) on delete restrict on update restrict;
create index ix_feeds_systemUser_5 on feeds (system_user_id);
alter table menus add constraint fk_menus_owner_6 foreign key (owner_id) references systemusers (id) on delete restrict on update restrict;
create index ix_menus_owner_6 on menus (owner_id);
alter table menuitems add constraint fk_menuitems_menu_7 foreign key (menu_id) references menus (id) on delete restrict on update restrict;
create index ix_menuitems_menu_7 on menuitems (menu_id);
alter table organizations add constraint fk_organizations_category_8 foreign key (category_id) references organizationcategories (id) on delete restrict on update restrict;
create index ix_organizations_category_8 on organizations (category_id);
alter table organizations add constraint fk_organizations_address_9 foreign key (address_id) references address (id) on delete restrict on update restrict;
create index ix_organizations_address_9 on organizations (address_id);
alter table organizations add constraint fk_organizations_billingAddress_10 foreign key (billing_address_id) references address (id) on delete restrict on update restrict;
create index ix_organizations_billingAddress_10 on organizations (billing_address_id);
alter table organizations add constraint fk_organizations_shippingAddress_11 foreign key (shipping_address_id) references address (id) on delete restrict on update restrict;
create index ix_organizations_shippingAddress_11 on organizations (shipping_address_id);
alter table persons add constraint fk_persons_category_12 foreign key (category_id) references personcategories (id) on delete restrict on update restrict;
create index ix_persons_category_12 on persons (category_id);
alter table persons add constraint fk_persons_address_13 foreign key (address_id) references address (id) on delete restrict on update restrict;
create index ix_persons_address_13 on persons (address_id);
alter table persons add constraint fk_persons_billingAddress_14 foreign key (billing_address_id) references address (id) on delete restrict on update restrict;
create index ix_persons_billingAddress_14 on persons (billing_address_id);
alter table persons add constraint fk_persons_shippingAddress_15 foreign key (shipping_address_id) references address (id) on delete restrict on update restrict;
create index ix_persons_shippingAddress_15 on persons (shipping_address_id);
alter table photos add constraint fk_photos_album_16 foreign key (album_id) references albums (id) on delete restrict on update restrict;
create index ix_photos_album_16 on photos (album_id);
alter table photos add constraint fk_photos_owner_17 foreign key (owner_id) references systemusers (id) on delete restrict on update restrict;
create index ix_photos_owner_17 on photos (owner_id);
alter table systemaccounts add constraint fk_systemaccounts_systemUser_18 foreign key (system_user_id) references systemusers (id) on delete restrict on update restrict;
create index ix_systemaccounts_systemUser_18 on systemaccounts (system_user_id);
alter table systemusers add constraint fk_systemusers_person_19 foreign key (person_id) references persons (id) on delete restrict on update restrict;
create index ix_systemusers_person_19 on systemusers (person_id);
alter table systemusers add constraint fk_systemusers_organization_20 foreign key (organization_id) references organizations (id) on delete restrict on update restrict;
create index ix_systemusers_organization_20 on systemusers (organization_id);
alter table systemusers add constraint fk_systemusers_userType_21 foreign key (user_type_id) references usertypes (id) on delete restrict on update restrict;
create index ix_systemusers_userType_21 on systemusers (user_type_id);
alter table videos add constraint fk_videos_album_22 foreign key (album_id) references albums (id) on delete restrict on update restrict;
create index ix_videos_album_22 on videos (album_id);
alter table videos add constraint fk_videos_owner_23 foreign key (owner_id) references systemusers (id) on delete restrict on update restrict;
create index ix_videos_owner_23 on videos (owner_id);



alter table menus_menuitems add constraint fk_menus_menuitems_menus_01 foreign key (menus_id) references menus (id) on delete restrict on update restrict;

alter table menus_menuitems add constraint fk_menus_menuitems_menuitems_02 foreign key (menuitems_id) references menuitems (id) on delete restrict on update restrict;

alter table organizations_organizationcategories add constraint fk_organizations_organizationcategories_organizations_01 foreign key (organizations_id) references organizations (id) on delete restrict on update restrict;

alter table organizations_organizationcategories add constraint fk_organizations_organizationcategories_organizationcategorie_02 foreign key (organizationcategories_id) references organizationcategories (id) on delete restrict on update restrict;

alter table persons_personcategories add constraint fk_persons_personcategories_persons_01 foreign key (persons_id) references persons (id) on delete restrict on update restrict;

alter table persons_personcategories add constraint fk_persons_personcategories_personcategories_02 foreign key (personcategories_id) references personcategories (id) on delete restrict on update restrict;

alter table persons_organizations add constraint fk_persons_organizations_persons_01 foreign key (persons_id) references persons (id) on delete restrict on update restrict;

alter table persons_organizations add constraint fk_persons_organizations_organizations_02 foreign key (organizations_id) references organizations (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table accounttype;

drop table address;

drop table albums;

drop table comments;

drop table feeds;

drop table menus;

drop table menus_menuitems;

drop table menuitems;

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

drop table usertypes;

drop table videos;

SET FOREIGN_KEY_CHECKS=1;

