package models

import java.time.LocalDateTime

case class Arguments(
                      start: LocalDateTime,
                      end: LocalDateTime,
                      intervals: Seq[Interval]
                    )
