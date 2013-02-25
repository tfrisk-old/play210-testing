# Tasks schema

# --- !Ups
ALTER TABLE task add assignee varchar(255)

# --- !Downs
ALTER TABLE task drop assignee varchar(255)
