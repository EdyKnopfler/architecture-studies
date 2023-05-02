import { useState } from "react";

import StepNavigator from "../stepNavigator/StepNavigator";
import { WeekSelector, incrementDaysTo } from "../weekSelector/WeekSelector";

export default function Flight({ destinationData, step }) {
  const [weekStart, setWeekStart] = useState(null);
  const weekEnd = incrementDaysTo(weekStart, 6);

  return (
    <>
      <StepNavigator step={step} />
      <h1>
        Voo para {destinationData.name}
        {' - '}
        {step === 'going' ? 'IDA' : 'VOLTA'}
      </h1>
      <WeekSelector refDate={new Date()} onWeekSelected={setWeekStart} />
      {weekStart && `${weekStart} - ${weekEnd}`}
    </>
  )
}