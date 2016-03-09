
INSERT INTO activity ( id, name, serial_id, creator_id, date_created, date_started, date_ended ) VALUES
  ( 1, 'ACT1', 'SID1', 'USER1', '2016-01-01', '2016-01-01', '2016-01-01' ),
  ( 2, 'ACT2', 'SID2', 'USER2', '2016-01-01', '2016-01-01', '2016-01-01' ),
  ( 3, 'ACT3', 'SID3', 'USER3', '2016-01-01', '2016-01-01', '2016-01-01' );


INSERT INTO signin ( id, date_created, user_id, activity_id ) VALUES
  ( 1, '2016-01-01', 'USER4', 1 ),
  ( 2, '2016-01-01', 'USER5', 1 ),
  ( 3, '2016-01-01', 'USER6', 2 ),
  ( 4, '2016-01-01', 'USER7', 2 ),
  ( 5, '2016-01-01', 'USER8', 3 );
