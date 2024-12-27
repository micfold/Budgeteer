package com.budgeteer

import io.micronaut.runtime.Micronaut

object Application {

	@JvmStatic
	fun main(args: Array<String>) {
		Micronaut.build()
			.packages("com.budgeteer")
			.mainClass(Application.javaClass)
			.start()
	}
}
