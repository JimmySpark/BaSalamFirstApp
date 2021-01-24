package mohagheghi.mahdi.data.mapper

interface Mapper<From, To> {
    fun mapFrom(from: From): To
}