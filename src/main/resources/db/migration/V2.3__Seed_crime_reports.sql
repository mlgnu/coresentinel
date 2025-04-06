-- Reports for Case 1: 'Warehouse Robbery' (C001)
INSERT INTO public.crime_reports (
    created_at, location, occurred_at, report_description, report_id, reporter_email, reporter_id, reporter_name, case_id
) VALUES
(NOW(), 'North District Warehouse', NOW() - INTERVAL '2 hours',
 'I was standing near the warehouse when I saw a group of masked individuals entering. They were carrying large bags and looked like they were armed. I didn’t hear any gunshots, but they forced their way inside the building.',
 'R001', 'witness1@example.com', 'P001', 'Emily Carter', 1),
(NOW(), 'North District Warehouse', NOW() - INTERVAL '4 hours',
 'As a security guard at the warehouse, I heard strange noises coming from the side door around 8:30 PM. When I went to check, I was confronted by two masked individuals. They forced me to leave, and I didn’t suffer any injuries, but I had to leave the area.',
 'R002', 'guard1@example.com', 'P002', 'James Monroe', 1),
(NOW(), 'North District Warehouse', NOW() - INTERVAL '3 hours',
 'I live nearby, and I heard a loud bang around 9:00 PM. At first, I didn’t think much of it, but when I went outside later, I saw two cars speeding away from the warehouse. It seemed suspicious to me.',
 'R003', 'resident1@example.com', 'P003', 'Tina Osei', 1),
(NOW(), 'North District Warehouse', NOW() - INTERVAL '1 day',
 'A few days ago, I saw some unfamiliar people loitering around the warehouse. They didn’t look like they belonged there, and I now realize they might have been planning the robbery. At the time, I didn’t think anything of it.',
 'R004', 'neighbor1@example.com', 'P004', 'Carlos Vega', 1);

-- Reports for Case 2: 'Serial Arson Incidents' (C002)
INSERT INTO public.crime_reports (
    created_at, location, occurred_at, report_description, report_id, reporter_email, reporter_id, reporter_name, case_id
) VALUES
(NOW(), 'Downtown - Abandoned Building', NOW() - INTERVAL '5 days',
 'I was passing by the old abandoned building around midnight when I saw a fire starting near the back. It started small but spread quickly. The fire department came within 15 minutes, but the building was almost completely destroyed by the time they arrived.',
 'R005', 'witness2@example.com', 'P005', 'Anita Shah', 2),
(NOW(), 'Downtown - Old Restaurant', NOW() - INTERVAL '3 days',
 'I was walking down the street when I noticed a person near the back door of the old restaurant. They had a hood on and looked suspicious. After a few minutes, I saw the flames start, and that’s when I realized what was happening.',
 'R006', 'passerby1@example.com', 'P006', 'Fred Juma', 2),
(NOW(), 'Downtown - Commercial District', NOW() - INTERVAL '7 days',
 'I work at a store nearby, and we have a security camera that captured a person carrying a gasoline can near the site of the fire. The individual was standing by the building, and the fire started shortly after they left. It was very strange.',
 'R007', 'store1@example.com', 'P007', 'Lina Zhao', 2);

-- Reports for Case 3: 'Homicide Investigation' (C003)
INSERT INTO public.crime_reports (
    created_at, location, occurred_at, report_description, report_id, reporter_email, reporter_id, reporter_name, case_id
) VALUES
(NOW(), 'East Sector - Victim’s Residence', NOW() - INTERVAL '2 days',
 'I live next door to the victim, and I heard loud shouting and what sounded like things being thrown around 10:30 PM. I didn’t check immediately, but when I found out about the murder the next morning, I realized it was connected to what I heard.',
 'R008', 'neighbor2@example.com', 'P008', 'Omar Idris', 3),
(NOW(), 'East Sector - Victim’s Residence', NOW() - INTERVAL '1 day',
 'I was called to the crime scene as part of the investigation. When we arrived, we noticed a broken window that had been shattered from the outside. It looked like someone had forced their way inside. The victim was found inside with multiple stab wounds, and we began to gather evidence immediately.',
 'R009', 'officer1@example.com', 'P009', 'Rita Brooks', 3),
(NOW(), 'East Sector - Victim’s Residence', NOW() - INTERVAL '3 days',
 'I own a small business nearby, and I saw a dark sedan parked near the victim’s house earlier in the day. The car was there for a while, and when I saw it leaving just before the murder was discovered, it seemed suspicious to me.',
 'R010', 'businessowner1@example.com', 'P010', 'Eli Cohen', 3);