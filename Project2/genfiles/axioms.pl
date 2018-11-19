:- ['grid.pl'].

goal_test(S):-
    ww_count(0,S).

% jon(X, Y, Z, s0):- 

jon(X, Y, Z, result(A,S)):- 
                            (
                                jon(X, Y, Z, S),
                                    forall(
                                            grid(MaxX, MaxY),
                                            (
                                                \+ (A = left); X = 0;
                                                \+ (A = right); X is MaxX-1;
                                                \+ (A = up); Y = 0;
                                                \+ (A = down); Y is MaxY-1
                                            )
                                    );
                                    (
                                        (
                                            obtsc(X1,Y1);
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
                                            (Z1 is Z);
                                            (\+ drgns(X, Y, Z2); Z is Z2)
                                        ) 
                                    );
                                    (
                                        A = kill_ww, 
                                        jon(X1,Y1,Z1,S), 
                                        Z1 > 0,
                                        Z is Z1-1
                                    )
                                ).