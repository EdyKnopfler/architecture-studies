import { useState } from "react";

import { getFlightsFromTo } from "./flightService";

import StepNavigator from "../stepNavigator/StepNavigator";
import { WeekSelector, incrementDaysTo } from "../weekSelector/WeekSelector";
import ScheduleMatrix from "../scheduleMatrix/ScheduleMatrix";

export default function Flight({ destinationData, step }) {
  const [flightSchedules, setFlightSchedules] = useState(null);

  async function weekSelected(weekStart) {
    const weekEnd = incrementDaysTo(weekStart, 6);
    const flights = await getFlightsFromTo(weekStart, weekEnd);
    setFlightSchedules(flights);
  }

  let flightTable = [];
  
  if (flightSchedules) {
    flightTable = flightSchedules.origins.map((airport) => {
      return (
        <>
          <h4>{airport.name}</h4>
          <ScheduleMatrix dates={airport.dates} />
        </>
      );
    });
  }

  return (
    <div className="Flight">
      <StepNavigator step={step} />
      <h3>
        Voo para {destinationData.name}
        {' - '}
        {step === 'going' ? 'IDA' : 'VOLTA'}
      </h3>
      <WeekSelector refDate={new Date()} onWeekSelected={weekSelected} />
      {flightSchedules && flightTable}
    </div>
  )
}