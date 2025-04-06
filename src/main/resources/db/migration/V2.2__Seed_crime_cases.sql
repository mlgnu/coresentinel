INSERT INTO public.crime_cases (
    area, case_id, case_name, case_type, city, created_at,
    description, level, status, creator_id
) VALUES
-- Case 1: MEDIUM level (created by Jamal Tariq - investigator)
('North District', 'C001', 'Warehouse Robbery', 'THEFT', 'Rivertown',
 NOW(),
 'On the evening of April 2nd, a group of armed suspects broke into a local warehouse situated on the outskirts of Rivertown. Surveillance footage reveals at least four individuals wearing masks and gloves. The robbers forced entry into the storage area, where they took valuable electronic goods, including high-end computers, televisions, and mobile devices. Local law enforcement has identified potential escape routes, but no arrests have been made as of yet. Investigators are currently reviewing CCTV footage and tracking down leads from witnesses in the area. At this point, the suspects have managed to evade capture, but the investigation is ongoing.',
 'MEDIUM', 'ONGOING', 5),

-- Case 2: HIGH level (created by Sophie Wong - investigator)
('Downtown', 'C002', 'Serial Arson Incidents', 'ARSON', 'Lakeview',
 NOW(),
 'A series of suspicious fires have been reported in the downtown area of Lakeview over the last month. The first incident involved the burning of an abandoned building, followed by two more within the span of a week. Each fire appears to have been deliberately set, and investigators are concerned that there may be a serial arsonist targeting the district. Witnesses have described a person seen near the locations of the fires, but no concrete evidence has yet been uncovered to positively identify a suspect. Fire department officials are collaborating with local law enforcement to analyze fire patterns and gather any potential clues that may lead to the culprit. As of now, the community is on high alert, and authorities are doing everything they can to prevent further damage.',
 'HIGH', 'PENDING', 6),

-- Case 3: CRITICAL level (created by Leo Kimani - investigator)
('East Sector', 'C003', 'Homicide Investigation', 'HOMICIDE', 'Greenfield',
 NOW(),
 'At approximately 11:45 PM on March 28th, a brutal homicide occurred in the East Sector of Greenfield. The victim, a well-known local entrepreneur, was found dead in their home with multiple stab wounds to the chest. Initial reports suggest that the murder was premeditated, as the suspect appeared to have gained access to the victim’s residence by force, bypassing security systems. Forensic experts are currently analyzing the crime scene for DNA evidence, fingerprints, and other potential clues that may identify the perpetrator. Local authorities are working alongside state investigators to gather information from anyone who might have seen unusual activity in the area before or after the incident. Given the high profile of the victim and the circumstances of the murder, this case has been classified as critical, and every effort is being made to apprehend the suspect.',
 'CRITICAL', 'ONGOING', 7);
