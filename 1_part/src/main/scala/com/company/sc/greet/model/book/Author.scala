package com.company.sc.greet.model.book

import java.util.UUID

case class Author(
    id: Option[UUID],
    name: String,
    surname: String
)
