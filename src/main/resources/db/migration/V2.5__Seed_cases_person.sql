-- Link persons to Crime Case 1: 'Warehouse Robbery' (C001)
INSERT INTO public.person_cases (person_id, case_id) VALUES
(1, 1),  -- Emily Carter (Witness) linked to 'Warehouse Robbery'
(2, 1),  -- James Monroe (Security Guard) linked to 'Warehouse Robbery'
(3, 1),  -- Tina Osei (Resident) linked to 'Warehouse Robbery'
(4, 1);  -- Carlos Vega (Neighbor) linked to 'Warehouse Robbery'

-- Link persons to Crime Case 2: 'Serial Arson Incidents' (C002)
INSERT INTO public.person_cases (person_id, case_id) VALUES
(5, 2),  -- Anita Shah (Witness) linked to 'Serial Arson Incidents'
(6, 2),  -- Fred Juma (Passerby) linked to 'Serial Arson Incidents'
(7, 2),  -- Lina Zhao (Store Owner) linked to 'Serial Arson Incidents'
(8, 2);  -- Omar Idris (Neighbor) linked to 'Serial Arson Incidents'

-- Link persons to Crime Case 3: 'Homicide Investigation' (C003)
INSERT INTO public.person_cases (person_id, case_id) VALUES
(9, 3),  -- Rita Brooks (Officer) linked to 'Homicide Investigation'
(10, 3), -- Eli Cohen (Business Owner) linked to 'Homicide Investigation'
(11, 3), -- Fred Juma (Passerby) linked to 'Homicide Investigation'
(12, 3); -- Lina Zhao (Store Owner) linked to 'Homicide Investigation'
