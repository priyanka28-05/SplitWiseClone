-- =====================
-- USERS
-- =====================
INSERT INTO users (user_id, user_name, email, oauth_id) VALUES (1, 'rami', 'rami@example.com', 'oauth-rami');
INSERT INTO users (user_id, user_name, email, oauth_id) VALUES (2, 'piyu', 'piyu@example.com', 'oauth-piyu');

-- =====================
-- GROUPS
-- =====================
INSERT INTO groups (group_id, group_name) VALUES (1, 'outing');

-- =====================
-- GROUP MEMBERS (Many-to-Many)
-- =====================
INSERT INTO group_members (group_id, user_id) VALUES (1, 1); -- rami
INSERT INTO group_members (group_id, user_id) VALUES (1, 2); -- piyu

-- =====================
-- EXPENSES
-- =====================
INSERT INTO expenses (expense_id, group_id, created_by, description, total_amount, created_at)
VALUES (1, 1, 1, 'vegie', 50.5, CURRENT_TIMESTAMP);

-- =====================
-- EXPENSE SHARES (split equally)
-- =====================
INSERT INTO expense_shares (expense_share_id, expense_id, user_id, share_amount, settled) VALUES (1, 1, 1, 25.25, FALSE); -- rami
INSERT INTO expense_shares (expense_share_id, expense_id, user_id, share_amount, settled) VALUES (2, 1, 2, 25.25, FALSE); -- piyu
