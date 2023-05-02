import { useState } from "react";

import StepNavigator from "../stepNavigator/StepNavigator";
import WeekSelector from "../weekSelector/WeekSelector";

export default function Flight({ destinationData, step }) {
  const [weekStart, setWeekStart] = useState(null);

  return (
    <>
      <StepNavigator step={step} />
      <h1>
        Voo para {destinationData.name}
        {' - '}
        {step === 'going' ? 'IDA' : 'VOLTA'}
      </h1>
      <WeekSelector refDate={new Date()} onWeekSelected={setWeekStart} />
      {for (let i = 0; i < 7; i++) {
        
      }}
    </>
  )
}