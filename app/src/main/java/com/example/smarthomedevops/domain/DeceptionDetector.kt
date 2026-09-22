package com.example.smarthomedevops.domain

data class DetectionResult (
    val isAttack:Boolean,
    val confidence:Int
)


class DeceptionDetector {

    private val patterns = listOf(
        Regex("""freeze|freezing|frozen|do\s+not\s+reduce""") to 5,
        Regex("""valve\s+(failure|failed|broken)|valve.*(failure|problem)|blowout|compression""") to 15,
        Regex("""electrical\s+(failure|issue|problem)|short\s+circuit|power\s+surge""") to 15,
        Regex("""structural\s+(crack|damage)|crack(ed)?\s+(wall|celinig|floor)""") to 15,
        Regex("""urgent|emergency|inmediately|critical|danger|imminent""") to 15
    )

    fun analyze(text:String):DetectionResult{
        val normalizedText=text.lowercase()
        var score=0
        for ((pattern, weight) in patterns) {
            if (pattern.containsMatchIn(normalizedText)) {
                score += weight
            }
        }

        score = score.coerceIn(0,100)

        return DetectionResult(
            isAttack = score >= 30,
            confidence = score
        )

    }
}