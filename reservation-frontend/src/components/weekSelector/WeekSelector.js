import { useState, useEffect } from "react";

import './WeekSelector.scss';

export function incrementDaysTo(date, days) {
  const newDate = new Date(date);
  newDate.setDate(newDate.getDate() + days);
  return newDate;
}

function weekRefSunday(refDate) {
  return incrementDaysTo(refDate, -refDate.getDay())
}

function twoZeros(n) {
  if (n < 10) {
    return `0${n}`;
  }

  return `${n}`;
}

function formatDate(date) {
  return (
    twoZeros(date.getDate()) + '/' + twoZeros(date.getMonth() + 1) + '/' +
    date.getFullYear()
  );
}

export function WeekSelector({ refDate, onWeekSelected }) {
  const [weekStart, setWeekStart] = useState(weekRefSunday(refDate));

  useEffect(() => {
    onWeekSelected(weekStart);
  }, []);
  
  function changeWeek(increment) {
    const newWeekStart = incrementDaysTo(weekStart, increment);
    setWeekStart(newWeekStart);
    onWeekSelected(newWeekStart);
  }

  const weekEnd = incrementDaysTo(weekStart, 6);

  return (
    <div className="WeekSelector clearFix">
      <div className="previous" onClick={() => changeWeek(-7)}>
        <img alt="Semana anterior" src="/arrow-left.png" />
      </div>
      <p>
        <b>{formatDate(weekStart)}</b>
        <span>a</span>
        <b>{formatDate(weekEnd)}</b>
      </p>
      <div className="next" onClick={() => changeWeek(+7)}>
        <img alt="Próxima semana" src="/arrow-right.png" />
      </div>
    </div>
  );
}