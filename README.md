Requirements:

Scoreboard can create a new match by taking two teams (homeTeam, awayTeam).
Match scores can be updated (homeScore, awayScore).
Match can finish, which will delete it from Scoreboard
Scoreboard can provide matches in order of highest to lowest total score
    If two matches have equal total score, sort those by most recent first

Plan:
Declare classes and functions as they are planned to be used
Implement tests
Implement classes and functions
implement given example

Reiterate if necessary


Thoughts:
I realized when writing the tests for the scoreboard class, that dropping the Team and Match classes
and using three lists (one for match names, one for score and one for start-time or start-order),
might be simpler. I believe the current solution is more scaleable though, as any of the classes
could be modified to store or provide more data.

Another path I chose not to follow was making the Match class a comparable class, nad having the
scoreboard hold asortable list, and continuously sort when adding/removing/modifying a game. Chose
not to do this, because it seemed like implementing the comparator logic and keeping track of which
Match should be greater than another seemed confusing. The comparator could determine "excitement"
of a match, which would means the list would have to be sorted in reverse before scoreboard returns
it. Or it could be sorted some other way.
