ALTER TABLE evidence
  ADD COLUMN tsv_resource tsvector GENERATED ALWAYS AS (
    CASE WHEN type = 'TEXT' THEN to_tsvector('english', resource)
         ELSE NULL
    END
  ) STORED;

CREATE INDEX idx_tsv_resource ON evidence USING GIN (tsv_resource);
