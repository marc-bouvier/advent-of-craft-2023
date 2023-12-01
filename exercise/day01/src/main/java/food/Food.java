package food;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Supplier;

public record Food(LocalDate expirationDate,
                   Boolean approvedForConsumption,
                   UUID inspectorId) {

    public boolean isEdible(Supplier<LocalDate> now) {

        boolean expiryDateNotPassed = expirationDate.isAfter(now.get());
        boolean wasInspected = inspectorId != null;

        return expiryDateNotPassed && approvedForConsumption && wasInspected;
    }
}

// What I did :
// - removed this prefix which does not add readability value
// - extracted variable to make more business sense
// - used one line return
// - Personal preference : added lines before and inside method
//   I find it easier to read the return statement as it is separated from the rest of the method body

// What I did not :
// - extract method for expiryDateNotPassed and wasInspected (premature in my opinion)

// 🤔 Not sure what UUID inspectorId stands for.
// I did my own guess and decided that it was the inspector that approved the food.
// ⚠️ If it was real production code I would have asked the business about inspector entity.

// I hesitated between one line and column return style.
// I opted for one line style since there are only 3 variables.
// It looked for easy to read in my opinion.
// I would have opted for column style if there were more than 3 variables.

// Column style
// return expiryDateNotPassed
//       && approvedForConsumption
//       && wasInspected;

// One line style
// return expiryDateNotPassed && approvedForConsumption && wasInspected;