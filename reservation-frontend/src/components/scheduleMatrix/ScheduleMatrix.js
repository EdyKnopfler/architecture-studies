import mountScheduleMatrixFrom from "./matrixMount";

import './ScheduleMatrix.scss';

export default function ScheduleMatrix({ dates }) {
  const matrix = mountScheduleMatrixFrom(dates);

  return (
    <div className="ScheduleMatrix">
      <table>
        <thead>
          <tr>
            <th></th>
            {matrix.uniqueHours.map((hour) => <td>{hour}</td>)}
          </tr>
        </thead>
        <tbody>
          {matrix.uniqueDates.map((date) => (
            <tr>
              <th>{date}</th>
              {matrix.uniqueHours.map((hour) => (
                matrix.positions[date][hour]
                  ? <td className="available"></td>
                  : <td className="unavailable"></td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}