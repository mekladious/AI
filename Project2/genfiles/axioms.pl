:- ['grid.pl'].

% jon(X, Y, Z, s0):- 

jon(X, Y, Z, result(A,S)):- 
                            (
                                jon(X, Y, Z, S),
                                    grid(MaxX, MaxY),
                                    (
                                        \+ (A = left); X = 0;
                                        \+ (A = right); X is MaxX-1;
                                        \+ (A = up); Y = 0;
                                        \+ (A = down); Y is MaxY-1
                                    ),
                                    (
                                        (
                                            obstc(X1,Y1);
                                            wwlkr(X1,Y1,_)
                                        ),
                                        (	
                                            \+ (A = left); X1 is X-1;
                                            \+ (A = right); X1 is X+1;
                                            \+ (A = up); Y1 is Y-1;
                                            \+ (A = down); Y1 is Y+1
                                        )
                                    );
                                    
									(
                                        \+ (A = kill_ww) ; Z = 0
                                    )
                            );
                            (
                                (
                                    (
                                        (
                                            jon(X1,Y,Z1,S),
                                            (
                                                (A = left,
                                                X1 is X+1);
                                                (A = right,
                                                X1 is X-1) 
                                            )
                                        );
                                        (
                                            jon(X,Y1,Z1,S),
                                            (
                                                (A = up,
                                                Y1 is Y+1);
                                                (A = down,
                                                Y1 is Y-1) 
                                            )
                                        )
                                    ),
                                    (
                                        (Z1 = Z);
                                        (\+ drgns(X, Y, Z2); Z = Z2)
                                    ) 
                                );
                                (
                                    A = kill_ww, 
                                    jon(X1,Y1,Z1,S), 
                                    Z1 > 0,
                                    Z is Z1-1
                                )
                            ).
wwlkr(X,Y,result(A,S)):-
							/*Action did not change previous situation*/
							( /*- action resulted in:*/ 
								wwlkr(X,Y,S),  
								(
									/*a is not kill*/
									\+ (A = kill_ww);
									
									/*if a is kill then jon snow is not in any adjacent cell or he does not have enough dragonstones*/
									(A = kill_ww) -> 
									(
										jon(X1,Y1,Z,S),
										\+(X is X1-1),
										\+(X is X1+1),
										\+(Y is Y1-1),
										\+(Y is Y1+1)
									);
									Z = 0
								)
							).
							/*
							Action resulted in a situation change.
							(
								if wwlkr was not there in previous state, it shouldn't be there in an intermediate state
							)
savewestros(,,R)*/
goal_test(S):-
    forall(
		(
			wwlkr(X,Y,s0)
		), 
		\+(wwlkr(X,Y,S))
	).