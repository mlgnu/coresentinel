-- Case 1: Image Evidence & Events
WITH case1_images AS (
  INSERT INTO public.evidence (created_at, deleted, evidence_id, remarks, resource, "type", case_id)
  VALUES
  (NOW(), FALSE, 'E012', 'No description provided', 'premium_photo-1682092603230-1ce7cf8ca451.jpg', 'IMAGE', 1),
  (NOW(), FALSE, 'E013', 'Image evidence showing the crime scene.', '9085345497_cff7eb045f_n.jpg', 'IMAGE', 1)
  RETURNING id
)
INSERT INTO public.evidence_events (created_at, evidence_id, resource_action, user_id)
SELECT NOW(), id, 'CREATE', u.user_id
FROM case1_images
CROSS JOIN (VALUES (6), (7)) AS u(user_id); -- 2 events per image (Jamal, Sophie)

-- Case 2: Image Evidence & Events
WITH case2_images AS (
  INSERT INTO public.evidence (created_at, deleted, evidence_id, remarks, resource, "type", case_id)
  VALUES
  (NOW(), FALSE, 'E014', 'Image for the crime scene', '9085345497_cff7eb045f_n.jpg', 'IMAGE', 2),
  (NOW(), FALSE, 'E015', 'Image evidence showing the suspect Named Thomas Andy.', '9085345497_cff7eb045f_n.jpg', 'IMAGE', 2)
  RETURNING id
)
INSERT INTO public.evidence_events (created_at, evidence_id, resource_action, user_id)
SELECT NOW(), id, 'CREATE', u.user_id
FROM case2_images
CROSS JOIN (VALUES (7), (8)) AS u(user_id); -- 2 events per image (Sophie, Leo)

-- Case 3: Image Evidence & Events
WITH case3_images AS (
  INSERT INTO public.evidence (created_at, deleted, evidence_id, remarks, resource, "type", case_id)
  VALUES
  (NOW(), FALSE, 'E016', 'Photo found in the crime scene for unknown person', 'pexels-photo-2379005.jpeg', 'IMAGE', 3)
  RETURNING id
)
INSERT INTO public.evidence_events (created_at, evidence_id, resource_action, user_id)
SELECT NOW(), id, 'CREATE', u.user_id
FROM case3_images
CROSS JOIN (VALUES (8), (9)) AS u(user_id); -- ✅ Fixed: Leo (8) and Ines (9)