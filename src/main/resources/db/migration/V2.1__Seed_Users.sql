-- PASSWORD = admin

INSERT INTO public.users (
    email, full_name, level, password, role, user_id, username
) VALUES
('admin@admin.com', 'Super Admin', 'HIGH',
 '$2a$10$l1bzbcOxOqE0p41YzHGFkeHVuhW3NqpCTiCbPjDBvlaaKrn/ky/tW',
 'ADMIN', 'A001', 'admin'),

('ella.stone@example.com', 'Ella Stone', 'LOW',
 '$2a$10$l1bzbcOxOqE0p41YzHGFkeHVuhW3NqpCTiCbPjDBvlaaKrn/ky/tW',
 'INVESTIGATOR', 'A002', 'ella.stone'),

('david.nguyen@example.com', 'David Nguyen', 'MEDIUM',
 '$2a$10$l1bzbcOxOqE0p41YzHGFkeHVuhW3NqpCTiCbPjDBvlaaKrn/ky/tW',
 'INVESTIGATOR', 'A003', 'david.nguyen'),

('nina.rosas@example.com', 'Nina Rosas', 'HIGH',
 '$2a$10$l1bzbcOxOqE0p41YzHGFkeHVuhW3NqpCTiCbPjDBvlaaKrn/ky/tW',
 'INVESTIGATOR', 'A004', 'nina.rosas'),

('eric.brooks@example.com', 'Eric Brooks', 'CRITICAL',
 '$2a$10$l1bzbcOxOqE0p41YzHGFkeHVuhW3NqpCTiCbPjDBvlaaKrn/ky/tW',
 'INVESTIGATOR', 'A005', 'eric.brooks'),

('jamal.tariq@example.com', 'Jamal Tariq', 'MEDIUM',
 '$2a$10$l1bzbcOxOqE0p41YzHGFkeHVuhW3NqpCTiCbPjDBvlaaKrn/ky/tW',
 'OFFICER', 'A006', 'jamal.tariq'),

('sophie.wong@example.com', 'Sophie Wong', 'HIGH',
 '$2a$10$l1bzbcOxOqE0p41YzHGFkeHVuhW3NqpCTiCbPjDBvlaaKrn/ky/tW',
 'OFFICER', 'A007', 'sophie.wong'),

('leo.kimani@example.com', 'Leo Kimani', 'CRITICAL',
 '$2a$10$l1bzbcOxOqE0p41YzHGFkeHVuhW3NqpCTiCbPjDBvlaaKrn/ky/tW',
 'OFFICER', 'A008', 'leo.kimani'),

('ines.fernandez@example.com', 'Ines Fernandez', 'LOW',
 '$2a$10$l1bzbcOxOqE0p41YzHGFkeHVuhW3NqpCTiCbPjDBvlaaKrn/ky/tW',
 'OFFICER', 'A009', 'ines.fernandez'),

('omar.syed@example.com', 'Omar Syed', 'MEDIUM',
 '$2a$10$l1bzbcOxOqE0p41YzHGFkeHVuhW3NqpCTiCbPjDBvlaaKrn/ky/tW',
 'OFFICER', 'A010', 'omar.syed'),

('maya.baker@example.com', 'Maya Baker', 'HIGH',
 '$2a$10$l1bzbcOxOqE0p41YzHGFkeHVuhW3NqpCTiCbPjDBvlaaKrn/ky/tW',
 'INVESTIGATOR', 'A011', 'maya.baker');