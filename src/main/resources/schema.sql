CREATE TABLE IF NOT EXISTS place (
	id bigint AUTO_INCREMENT primary key,
	name VARCHAR(255) not null,
	slug VARCHAR(255) not null,
	state VARCHAR(255) not null,
	created_at timestamp not null,
	updated_at timestamp not null
);