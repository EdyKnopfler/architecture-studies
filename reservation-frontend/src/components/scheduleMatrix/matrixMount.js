export default function mountScheduleMatrixFrom(dates) {
  const uniqueDates = Object.keys(
    dates.reduce(
      (partial, date) => ({...partial, [date.date]: true}),
      {}
    )
  )

  const uniqueHours = Object.keys(
    dates.reduce(
      (partial, date) => ({
        ...partial,
        ...date.hours.reduce((partial, hour) => ({...partial, [hour]: true}), {})
      }),
      {}
    )
  );

  uniqueDates.sort();
  uniqueHours.sort();

  const positions = dates.reduce(
    (partial, date) => ({
      ...partial,
      [date.date]: date.hours.reduce(
        (partial, hour) => ({
          ...partial,
          [hour]: true
        }),
        {}
      )
    }),
    {}
  )

  return { uniqueDates, uniqueHours, positions };
}