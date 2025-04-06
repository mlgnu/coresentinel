-- Case 1 Evidence
WITH case1_evidence AS (
  INSERT INTO public.evidence (created_at, deleted, evidence_id, resource, remarks, "type", case_id)
  VALUES
  (NOW(), FALSE, 'E001', 'Sample text evidence.', 'internal_system', 'TEXT', 1),
  (NOW(), FALSE, 'E002', 'Additional text evidence.', 'internal_system', 'TEXT', 1),
  (NOW(), FALSE, 'E003', 'Further clarification.', 'internal_system', 'TEXT', 1)
  RETURNING id
)
INSERT INTO public.evidence_events (created_at, evidence_id, resource_action, user_id)
SELECT NOW(), id, 'CREATE', u.user_id
FROM case1_evidence
CROSS JOIN (VALUES (6), (7), (8)) AS u(user_id); -- Jamal, Sophie, Leo

-- Case 2 Evidence
WITH case2_evidence AS (
  INSERT INTO public.evidence (created_at, deleted, evidence_id, resource, remarks, "type", case_id)
  VALUES
  (NOW(), FALSE, 'E004', 'Text evidence for case 2.', 'internal_system', 'TEXT', 2),
  (NOW(), FALSE, 'E005', 'Additional evidence.', NULL, 'TEXT', 2),
  (NOW(), FALSE, 'E006', 'Detailed evidence.', NULL, 'TEXT', 2),
  (NOW(), FALSE, 'E007', 'Supporting evidence.', 'internal_system', 'TEXT', 2)
  RETURNING id
)
INSERT INTO public.evidence_events (created_at, evidence_id, resource_action, user_id)
SELECT NOW(), id, 'CREATE', u.user_id
FROM case2_evidence
CROSS JOIN (VALUES (7), (8), (9)) AS u(user_id); -- Sophie, Leo, Ines

-- Case 3 Evidence
WITH case3_evidence AS (
  INSERT INTO public.evidence (created_at, deleted, evidence_id, resource, remarks, "type", case_id)
  VALUES
  (NOW(), FALSE, 'E008', 'Evidence for case 3.', 'internal_system', 'TEXT', 3),
  (NOW(), FALSE, 'E009', 'Second piece.', NULL, 'TEXT', 3),
  (NOW(), FALSE, 'E010', 'Additional details.', 'internal_system', 'TEXT', 3),
  (NOW(), FALSE, 'E011', 'Final piece.', 'internal_system', 'TEXT', 3)
  RETURNING id
)
INSERT INTO public.evidence_events (created_at, evidence_id, resource_action, user_id)
SELECT NOW(), id, 'CREATE', u.user_id
FROM case3_evidence
CROSS JOIN (VALUES (8), (9), (10)) AS u(user_id); -- Leo, Ines, Omar